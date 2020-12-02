package com.bingo.springbatch.joblaunch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
