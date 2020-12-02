package com.bingo.springbatch.config;

import com.google.common.collect.Lists;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description: 多线程step
 */
//@Configuration
public class MultiThreadStepDemoJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TaskExecutor aTaskExecutor;

    @Bean
    public Job multiThreadStepJob() {
        return jobBuilderFactory.get("multiThreadStepJob1")
                .start(multiThreadStepStep())
                .build();
    }

    @Bean
    public Step multiThreadStepStep() {
        return stepBuilderFactory.get("multiThreadStepStep")
                .<Integer, Integer>chunk(10)
                .reader(reader())
                .writer(writer())
                .taskExecutor(aTaskExecutor)
                .throttleLimit(20)//限制能使用最大的线程数
                .build();
    }

    @Bean
    public TaskExecutor aTaskExecutor() {
        return new SimpleAsyncTaskExecutor("springBatch_");
    }

    @Bean
    public ItemWriter<Integer> writer() {
        return items -> items.forEach(item -> System.out.println("write -> " + item + " " + Thread.currentThread().getName()));
    }


    @Bean
    public ListItemReader<Integer> reader() {
        List<Integer> item = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            item.add(i);
        }
        System.out.println("reader -> " + Thread.currentThread().getName());
        return new ListItemReader<>(item);
    }

}
