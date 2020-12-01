package com.bingo.springbatch.retry;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @description: 错误重试
 */
@Component("retryProcessor")
public class RetryProcessor implements ItemProcessor<Integer, Integer> {

    private int retryCount = 0;

    @Override
    public Integer process(Integer item) throws Exception {
        System.out.println("start process -> " + item);
        if (item.equals(26)) {
            retryCount++;
            if (retryCount <= 3) {
                System.out.println("error! will retry...");
                throw new RetryException();
            } else {
                return item;
            }
        } else {
            return item;
        }
    }
}
