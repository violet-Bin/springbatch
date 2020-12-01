package com.bingo.springbatch.skip;

import com.bingo.springbatch.itemwriterdb.ItemWriterToDBJob;
import com.google.common.collect.Lists;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description: 错误跳过
 */
@Configuration
public class SkipDemoJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("skipProcessor")
    private ItemProcessor<String, String> skipProcessor;

    @Autowired
    @Qualifier("skipWriter")
    private SkipWriter skipWriter;

    @Autowired
    @Qualifier("mySkipListener")
    private SkipListener<String, String> mySkipListener;

    @Bean
    public Job skipJob() {
        return jobBuilderFactory.get("skipJob4")
                .start(skipStep())//执行step.
                .build();
    }

    @Bean
    public Step skipStep() {
        return stepBuilderFactory.get("skipStep")
                .<String, String>chunk(10)
                .reader(reader())
                .processor(skipProcessor)
                .writer(skipWriter)
                .faultTolerant()
                .skip(SkipException.class)
                .skipLimit(5) //reade、write、process总的跳过次数，如果超过了，就会停止任务
                .listener(mySkipListener)//错误跳过监听器
                .build();
    }

    @Bean
    public ListItemReader<? extends String> reader() {
        List<String> item = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            item.add("A" + i);
        }
        return new ListItemReader<>(item);
    }


}
