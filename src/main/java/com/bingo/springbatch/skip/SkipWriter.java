package com.bingo.springbatch.skip;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("skipWriter")
public class SkipWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(System.out::println);
    }
}
