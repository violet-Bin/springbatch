package com.bingo.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @author: jiangjiabin
 * @date: Create in 21:43 2020/11/29
 * @description: split实现step并发执行
 */
//@Configuration
public class SplitDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job splitDemoJob() {
        return jobBuilderFactory.get("splitDemoJob4")
                .start(splitDemoFlow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(splitDemoFlow2())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step1...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step2...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step3...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }


    @Bean
    public Flow splitDemoFlow1() {
        return new FlowBuilder<Flow>("splitDemoFlow1")
                .start(step1())
                .build();
    }

    @Bean
    public Flow splitDemoFlow2() {
        return new FlowBuilder<Flow>("splitDemoFlow2")
                .start(step2())
                .next(step3())
                .build();
    }


}
