/*
 *
 *  * Copyright (c) 2019. MK Groups.
 *  * All rights reserved.
 *  * All data of MK groups are confidential.
 *
 */

package com.mohan.springboot.app.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author MOHANKUMAR
 */
@Slf4j
public class SimpleJob extends QuartzJobBean {
    @Override
    protected synchronized void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("                                             ");
        log.info("                                             ");
        log.info("SimpleJob Job Name.........................{}", jobExecutionContext.getJobDetail().getKey().getName());
        log.info("SimpleJob Job Priority.....................{}", jobExecutionContext.getTrigger().getPriority());
        log.info("SimpleJob ScheduledFireTime................{}", jobExecutionContext.getScheduledFireTime());
        log.info("SimpleJob NextFireTime.....................{}", jobExecutionContext.getNextFireTime());
        log.info("                                             ");
        log.info("                                             ");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
}
