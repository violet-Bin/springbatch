package com.bingo.springbatch.config;

import com.bingo.springbatch.listener.MyChunkListener;
import com.bingo.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author: jiangjiabin
 * @description:
 */
//@Configuration
public class ListenerDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerDemoJob() {
        return jobBuilderFactory.get("listenerDemoJob9")//job名
                .start(step1())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")//step名
                .<String, String>chunk(2)//读取类型和写出类型
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(read())
                .writer(write())
                .throttleLimit(2)
                .build();

    }

    @Bean
    public ItemWriter<String> write() {
        return list -> list.forEach(System.out::println);
    }

    @Bean
    public ItemReader<String> read() {
        return new ListItemReader<>(Arrays.asList("1", "2", "3"));
    }


}
