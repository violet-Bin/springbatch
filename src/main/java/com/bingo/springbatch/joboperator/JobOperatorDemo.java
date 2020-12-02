package com.bingo.springbatch.joboperator;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @description: JobOperator
 */
//@Component
//@EnableScheduling
public class JobOperatorDemo {

    @Autowired
    private JobOperator jobOperator;

    @Scheduled(cron = "*/5 * * * * ?")
    public void startTask() throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        //执行任务并传参数, 格式k=v, 不用注入对象,
        // (JobLauncher时需要封装一个JobParameter对象，且注入bean)
        jobOperator.start("jobOperatorJob1", "param=1234");
    }
}
