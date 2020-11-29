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

/**
 * @author: jiangjiabin
 * @date: Create in 21:31 2020/11/29
 * @description: Flow
 */
//@Configuration
public class FlowDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowDemoJob() {
        return jobBuilderFactory.get("flowDemoJob")//job名
                .start(flowDemoFlow())//执行step.
                .next(step3())
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

    /**
     * 创建Flow对象，指明包含哪些step
     * @return Flow
     */
    @Bean
    public Flow flowDemoFlow() {
        return new FlowBuilder<Flow>("flowDemoFlow")
                .start(step1())
                .next(step2())
                .build();
    }





}
