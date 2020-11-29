package com.bingo.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author: jiangjiabin
 * @date: Create in 22:12 2020/11/29
 * @description: job嵌套
 */
//@Configuration
public class ParentJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Job child1DemoJob;

    @Autowired
    private Job child2DemoJob;

    @Autowired
    private JobLauncher launcher;

    @Bean
    public Job parentDemoJob(JobRepository repository, PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("parentDemoJob")//job名
                .start(childJob1(repository, transactionManager))
                .next(childJob2(repository, transactionManager))
                .build();
    }

    //返回的是Step类型的Job  转换
    private Step childJob2(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(child2DemoJob)
                .launcher(launcher)//使用启动父Job的启动对象
                .repository(repository)//job的持久化对象
                .transactionManager(transactionManager)//事务管理器
                .build();

    }

    private Step childJob1(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(child1DemoJob)
                .launcher(launcher)//使用启动父Job的启动对象
                .repository(repository)//job的持久化对象
                .transactionManager(transactionManager)//事务管理器
                .build();
    }

}
