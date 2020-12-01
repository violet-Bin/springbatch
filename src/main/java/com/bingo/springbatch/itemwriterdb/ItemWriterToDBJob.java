package com.bingo.springbatch.itemwriterdb;

import com.bingo.springbatch.itemreaderfromfile.Customer;
import com.bingo.springbatch.restartreader.RestartReader;
import com.bingo.springbatch.restartreader.RestartWriter;
import com.google.common.collect.Lists;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description: 写入DB
 */
//@Configuration
public class ItemWriterToDBJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileReader")
    private ItemReader<Customer> flatFileReader;

    @Autowired
    @Qualifier("itemWriterDB")
    private ItemWriter<Customer> itemWriterDB;

    @Autowired
    @Qualifier("customerUpperProcessor")
    private ItemProcessor<Customer, Customer> customerUpperProcessor;

    @Bean
    public Job itemWriterDBJob() {
        return jobBuilderFactory.get("itemWriterDBJob1")
                .start(itemWriterDBStep())//执行step.
                .build();
    }

    @Bean
    public Step itemWriterDBStep() {
        return stepBuilderFactory.get("itemWriterDBStep")
                .<Customer, Customer>chunk(2)
                .reader(flatFileReader)
                .writer(itemWriterDB)
                .processor(processor()) //多种处理方式
                .build();
    }

    //有多种处理方式
    @Bean
    public CompositeItemProcessor<Customer, Customer> processor() {
        CompositeItemProcessor<Customer, Customer> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<Customer, Customer>> delegateProcessor = Lists.newArrayList();
        delegateProcessor.add(customerUpperProcessor);
        //...add other processor
        processor.setDelegates(delegateProcessor);
        return processor;
    }


}
