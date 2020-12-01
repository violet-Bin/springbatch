package com.bingo.springbatch.restartreader;

import com.bingo.springbatch.itemreaderfromdb.User;
import com.bingo.springbatch.itemreaderfromfile.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiangjiabin
 * @description: 可中断任务
 */
//@Configuration
public class ReStartItemReaderJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RestartReader restartReader;

    @Autowired
    private RestartWriter restartWriter;

    @Bean
    public Job restartReaderJob() {
        return jobBuilderFactory.get("restartReaderJob6")
                .start(restartReaderStep())//执行step.
                .build();
    }

    @Bean
    public Step restartReaderStep() {
        return stepBuilderFactory.get("itemReaderDBStep")
                .<Customer, Customer>chunk(2)
                .reader(restartReader)
                .writer(restartWriter)
                .build();
    }
}
