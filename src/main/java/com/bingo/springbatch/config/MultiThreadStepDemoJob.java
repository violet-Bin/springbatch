package com.bingo.springbatch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: jiangjiabin
 * @description: 多线程step
 */
//@Configuration
public class MultiThreadStepDemoJob implements StepExecutionListener {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Bean
    public Job multiThreadStepJob() {
        return jobBuilderFactory.get("multiThreadStepJob15")
                .start(multiThreadStepStep())
                .build();
    }

    @Bean
    public Step multiThreadStepStep() {
        return stepBuilderFactory.get("multiThreadStepStep")
                .<Integer, Integer>chunk(9)
                .reader(reader())
                .writer(writer())
                .taskExecutor(new ThreadPoolTaskExecutor())
                .throttleLimit(20)//限制能使用最大的线程数
                .listener(this)
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
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("reader -> " + Thread.currentThread().getName());
        return new ListItemReader<>(list);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
