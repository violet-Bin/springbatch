package com.bingo.springbatch.itemreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
//@Configuration
public class ItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderDemoJob() {
        return jobBuilderFactory.get("itemReaderDemoJob1")//job名
                .start(itemReaderStep())//执行step.
                .build();
    }

    @Bean
    public Step itemReaderStep() {
        return stepBuilderFactory.get("itemReaderStep")//step名
                .<String, String>chunk(2)
                .reader(itemReaderDemoReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }


    @Bean
    public MyReader itemReaderDemoReader() {
        List<String> datas = Arrays.asList("duck", "chicken");
        return new MyReader(datas);
    }
}
