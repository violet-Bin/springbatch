package com.bingo.springbatch.joboperator;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author: jiangjiabin
 * @description:
 */
@Configuration
public class JobOperatorDemoJob implements StepExecutionListener, ApplicationContextAware {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRegistry opJobRegistry;

    private ApplicationContext context;

    private Map<String, JobParameter> parameters;

    @Bean
    public Job jobOperatorJob() {
        return jobBuilderFactory.get("jobOperatorJob6")
                .start(jobOperatorStep())
                .build();
    }

    @Bean
    public Step jobOperatorStep() {
        return stepBuilderFactory.get("jobOperatorStep")
                .listener(this)
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("jobOperatorStep..." + parameters.get("param").getValue());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public JobOperator jobOperator() {
        SimpleJobOperator operator = new SimpleJobOperator();

        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());//参数转换，将k=v形式的参数转换为JobParameter对象
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);//jobExplorer可以获取任务相关信息
        operator.setJobRegistry(opJobRegistry);//注册任务

        return operator;
    }

    @Bean
    public JobRegistryBeanPostProcessor opJobRegistry() throws Exception {
        JobRegistryBeanPostProcessor jobRegistry = new JobRegistryBeanPostProcessor();

        jobRegistry.setJobRegistry(this.opJobRegistry);
        jobRegistry.setBeanFactory(context.getAutowireCapableBeanFactory());
        jobRegistry.afterPropertiesSet();

        return jobRegistry;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
