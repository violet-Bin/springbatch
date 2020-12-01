package com.bingo.springbatch.retry;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("retryWriter")
public class RetryWriter implements ItemWriter<Integer> {

    @Override
    public void write(List<? extends Integer> items) throws Exception {
        items.forEach(System.out::println);
    }
}
