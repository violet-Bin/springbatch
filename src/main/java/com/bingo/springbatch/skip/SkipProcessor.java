package com.bingo.springbatch.skip;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("skipProcessor")
public class SkipProcessor implements ItemProcessor<String, String> {

    private int retryCount = 0;

    @Override
    public String process(String item) throws Exception {
        System.out.println("start process -> " + item);
        if (item.equalsIgnoreCase("A26")) {
            retryCount++;
            if (retryCount <= 3) {
                System.out.println("error! will skip this item... -> " + item);
                throw new SkipException();
            } else {
                return item;
            }
        } else {
            return item;
        }
    }
}
