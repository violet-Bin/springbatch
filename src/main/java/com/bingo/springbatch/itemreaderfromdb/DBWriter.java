package com.bingo.springbatch.itemreaderfromdb;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("dbwriter")
public class DBWriter implements ItemWriter<User> {

    @Override
    public void write(List<? extends User> list) throws Exception {
        list.forEach(System.out::println);
    }
}
