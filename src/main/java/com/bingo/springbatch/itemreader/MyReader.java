package com.bingo.springbatch.itemreader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * @author: jiangjiabin
 * @description:
 */
public class MyReader implements ItemReader<String> {

    private Iterator<String> iterator;

    public MyReader(List<String> list) {
        iterator = list.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
}
