package com.bingo.springbatch.itemreaderfromfile;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("flatFileWriter")
public class FlatFileWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(System.out::println);
    }
}
