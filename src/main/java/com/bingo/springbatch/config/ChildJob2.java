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
//@Configuration
public class ChildJob2 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //在配置文件中配置启动的父job名
    @Bean
    public Job child2DemoJob() {
        return jobBuilderFactory.get("child2DemoJob")//job名
                .start(step2())//执行step.
                .next(step3())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("child2 step2...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("child2 step3...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

}
