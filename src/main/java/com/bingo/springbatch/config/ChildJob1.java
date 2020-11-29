package com.bingo.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiangjiabin
 * @date: Create in 22:06 2020/11/29
 * @description:
 */
@Configuration
public class ChildJob1 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job child1DemoJob() {
        return jobBuilderFactory.get("child1DemoJob")//job名
                .start(step1())//执行step.
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("child1 step1...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

}
