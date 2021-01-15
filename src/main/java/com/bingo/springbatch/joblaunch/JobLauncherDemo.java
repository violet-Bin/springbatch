package com.bingo.springbatch.joblaunch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @description: 作业调度
 * 需要关闭自动执行任务，application.xml中加入
 * spring.batch.job.enabled=false
 */
//@Component("jobLauncherDemo")
//@EnableScheduling
public class JobLauncherDemo {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job jobLauncherJob;

    @Scheduled(cron = "*/5 * * * * ?")
    public void startTask() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        launcher();
    }

    public void launcher() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(jobLauncherJob, getJobParameters());
    }

    private JobParameters getJobParameters() {
        return new JobParametersBuilder()
                .addString("jobParameters", "lalala")
                .toJobParameters();
    }
}
