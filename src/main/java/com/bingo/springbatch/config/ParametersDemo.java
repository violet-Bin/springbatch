package com.bingo.springbatch.config;

import com.bingo.springbatch.listener.MyJobListener;
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
public class ParametersDemo implements StepExecutionListener {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //arguments
    private Map<String, JobParameter> parameters;

    @Bean
    public Job parameterDemoJob() {
        return jobBuilderFactory.get("parameterDemoJob")
                .start(parameterStep())
                .build();
    }

    //Job执行的是step，Job使用的数据肯定实在step中使用
    //那只需给step传递数据。如何传输？
    //使用监听器传递数据
    @Bean
    public Step parameterStep() {
        return stepBuilderFactory.get("parameterStep")//step名
                .listener(this)
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(parameters.get("info"));
                    return RepeatStatus.FINISHED;//返回执行状态
                })
                .build();
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
