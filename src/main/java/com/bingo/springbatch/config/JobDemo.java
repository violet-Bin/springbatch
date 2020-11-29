package com.bingo.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiangjiabin
 * @date: Create in 20:15 2020/11/29
 * @description: 配置类
 */
//@Configuration
public class JobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobDemoJob() {
//        return jobBuilderFactory.get("jobDemoJob")//执行多个
//                .start(step1())
//                .next(step2())
//                .next(step3())
//                .build();
        return jobBuilderFactory.get("jobDemoJob")
                .start(step1()).on("COMPLETED")//条件
                .to(step2())
                .from(step2()).on("COMPLETED")//fail()、stopAndRestart()
                .to(step3())
                .from(step3()).end()
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
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step2...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step3...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }


}
