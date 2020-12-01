package com.bingo.springbatch.retry;

import com.google.common.collect.Lists;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description: 错误重试
 */
//@Configuration
public class RetryDemoJos {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("retryProcessor")
    private ItemProcessor<Integer, Integer> retryProcessor;

    @Autowired
    @Qualifier("retryWriter")
    private RetryWriter retryWriter;

    @Bean
    public Job retryJob() {
        return jobBuilderFactory.get("retryJob")
                .start(retryStep())//执行step.
                .build();
    }

    @Bean
    public Step retryStep() {
        return stepBuilderFactory.get("retryStep")
                .<Integer, Integer>chunk(10)
                .reader(retryReader())
                .processor(retryProcessor)
                .writer(retryWriter)
                .faultTolerant()//容错
                .retry(RetryException.class)
                .retryLimit(5)//reade、write、process总的重试次数，如果超过了，就会停止任务
                .build();
    }

    @Bean
    public ListItemReader<Integer> retryReader() {
        List<Integer> item = Lists.newArrayList();
        for (int i = 0; i < 60; i++) {
            item.add(i);
        }
        return new ListItemReader<>(item);
    }


}
