package com.bingo.springbatch.config;

import com.bingo.springbatch.decider.MyDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiangjiabin
 * @description: 决策器 满足某个条件进入下一步
 */
//@Configuration
public class DeciderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job deciderDemoJob() {
        return jobBuilderFactory.get("deciderDemoJob")//job名
                .start(step1())//执行step.
                .next(myDecider())
                .from(myDecider()).on("even").to(step2())
                .from(myDecider()).on("odd").to(step3())
                .from(step3()).on("*").to(myDecider())//因为count++为1，先执行step3()，step3执行后再到决策器，count++后还会执行step2
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
                    System.out.println("even...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")//step名
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("odd...");
                    return RepeatStatus.FINISHED;//返回执行状态
                }).build();
    }

    @Bean
    public JobExecutionDecider myDecider() {
        return new MyDecider();
    }



}
