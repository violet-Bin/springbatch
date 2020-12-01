package com.bingo.springbatch.skip;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @description: 错误跳过监听器
 */
@Component("mySkipListener")
public class MySkipListener implements SkipListener<String, String> {

    //读的时候跳过的数据
    @Override
    public void onSkipInRead(Throwable throwable) {
        //...
    }

    //写的时候跳过的数据
    @Override
    public void onSkipInWrite(String s, Throwable throwable) {
        //...
    }

    //处理的时候跳过的数据
    @Override
    public void onSkipInProcess(String item, Throwable throwable) {
        System.out.println(item + " occur exception! " + throwable);
    }
}
