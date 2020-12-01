package com.bingo.springbatch.restartreader;

import com.bingo.springbatch.itemreaderfromfile.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("restartWriter")
public class RestartWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(System.out::println);
    }
}
