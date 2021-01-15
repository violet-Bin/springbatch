package com.bingo.springbatch.joblaunch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author: jiangjiabin
 * @description:
 */
//@Configuration
public class JobLauncherDemoJob implements StepExecutionListener {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters;

    @Bean
    public Job jobLauncherJob() {
        return jobBuilderFactory.get("jobLauncherJob2")
                .start(jobLauncherStep())
                .build();
    }

    @Bean
    public Step jobLauncherStep() {
        return stepBuilderFactory.get("jobLauncherStep")
                .listener(this)
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("jobLauncherStep..." + parameters.get("jobParameters").getValue());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
