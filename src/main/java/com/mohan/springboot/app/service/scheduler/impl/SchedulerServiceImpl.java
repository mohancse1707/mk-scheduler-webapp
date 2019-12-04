/*
 *
 *  * Copyright (c) 2019. MK Groups.
 *  * All rights reserved.
 *  * All data of MK groups are confidential.
 *
 */

package com.mohan.springboot.app.service.scheduler.impl;

import com.mohan.springboot.app.config.scheduler.JobScheduleCreator;
import com.mohan.springboot.app.entity.scheduler.JobSchedulerDetails;
import com.mohan.springboot.app.repository.scheduler.SchedulerRepository;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;
import com.mohan.springboot.app.service.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author MOHANKUMAR
 */
@Slf4j
@Transactional
@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JobScheduleCreator scheduleCreator;

    private static final String JOB_STATUS_CREATED = "CREATED";
    private static final String JOB_STATUS_CANCELLED = "CANCELLED";
    private static final String JOB_STATUS_CHANGED = "CHANGED";


    @Override
    public List<JobSchedulerDetailsDTO> fetchAllJobSchedulers(String jobName, Long priority, int pageIndex, int pageSize) {
        return schedulerRepository.fetchAllJobSchedulers(jobName, priority, pageIndex, pageSize);
    }

    @Override
    public int fetchJobSchedulersCount(String jobName, Long priority) {
        return schedulerRepository.fetchJobSchedulersCount(jobName, priority);
    }

    @Override
    public void startAllSchedulers() {
        List<JobSchedulerDetails> jobSchedulerList = schedulerRepository.findAll();
        log.info("jobSchedulerList Size {}", jobSchedulerList.size());
        if (jobSchedulerList != null) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            jobSchedulerList.forEach(jobInfo -> {
                try {

                    if(!jobInfo.getJobStatus().equalsIgnoreCase(JOB_STATUS_CANCELLED)){
                        JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                            .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
                        if (!scheduler.checkExists(jobDetail.getKey())) {
                            Trigger trigger;
                            jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()),
                                false, context, jobInfo.getJobName(), jobInfo.getJobGroup());
                            //Long milliSeconds = (jobInfo.getFrequency() * 1000);
                            Date startDate = new Date();
                            trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), startDate,
                                jobInfo.getFrequency(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW, jobInfo.getPriority());

                            scheduler.scheduleJob(jobDetail, trigger);

                        }
                    }
                } catch (ClassNotFoundException e) {
                    log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
                } catch (SchedulerException e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
    }

    @Override
    public boolean scheduleNewJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO) {
        log.info("scheduleNewJob Initiated");
        boolean result = false;
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobSchedulerDetailsDTO.getJobClass()))
                    .withIdentity(jobSchedulerDetailsDTO.getJobName(), jobSchedulerDetailsDTO.getJobGroup()).build();
            if (!scheduler.checkExists(jobDetail.getKey())) {

                jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobSchedulerDetailsDTO.getJobClass()),
                        false, context, jobSchedulerDetailsDTO.getJobName(), jobSchedulerDetailsDTO.getJobGroup());

                Trigger trigger;
                //Long milliSeconds = (jobSchedulerDetailsDTO.getFrequency() * 1000);
                Date startDate = new Date();
                trigger = scheduleCreator.createCronTrigger(jobSchedulerDetailsDTO.getJobName(), startDate, jobSchedulerDetailsDTO.getFrequency(),
                        SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW, jobSchedulerDetailsDTO.getPriority());

                scheduler.scheduleJob(jobDetail, trigger);
                JobSchedulerDetails jobSchedulerDetail = new JobSchedulerDetails();
                BeanUtils.copyProperties(jobSchedulerDetailsDTO, jobSchedulerDetail);
                jobSchedulerDetail.setStartDate(startDate);
                jobSchedulerDetail.setJobStatus(JOB_STATUS_CREATED);
                schedulerRepository.save(jobSchedulerDetail);
                result = true;
            } else {
                result = false;
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class Not Found - {}", jobSchedulerDetailsDTO.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        log.info("scheduleNewJob completed");
        return result;
    }

    @Override
    public boolean updateScheduleJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO) {
        log.info("updateScheduleJob Initiated");
        boolean result = false;
        Trigger newTrigger;
        //Long milliSeconds = (jobSchedulerDetailsDTO.getFrequency() * 1000);
        Date startDate = new Date();
        newTrigger = scheduleCreator.createCronTrigger(jobSchedulerDetailsDTO.getJobName(), startDate, jobSchedulerDetailsDTO.getFrequency(),
                SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW, jobSchedulerDetailsDTO.getPriority());

        try {
            schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobSchedulerDetailsDTO.getJobName()), newTrigger);
            JobSchedulerDetails jobSchedulerDetail = new JobSchedulerDetails();
            BeanUtils.copyProperties(jobSchedulerDetailsDTO, jobSchedulerDetail);
            jobSchedulerDetail.setJobStatus(JOB_STATUS_CHANGED);
            jobSchedulerDetail.setStartDate(startDate);
            schedulerRepository.save(jobSchedulerDetail);
            result = true;
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        log.info("updateScheduleJob completed");
        return result;
    }

    @Override
    public boolean unScheduleJob(String jobName) {
        try {
            return schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName));
        } catch (SchedulerException e) {
            log.error("Failed to un-schedule job - {}", jobName, e);
            return false;
        }
    }

    @Override
    public boolean deleteJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO) {
        boolean result = false;
        try {
            result = schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobSchedulerDetailsDTO.getJobName(), jobSchedulerDetailsDTO.getJobGroup()));
            JobSchedulerDetails jobSchedulerDetail = new JobSchedulerDetails();
            BeanUtils.copyProperties(jobSchedulerDetailsDTO, jobSchedulerDetail);
            jobSchedulerDetail.setJobStatus(JOB_STATUS_CANCELLED);
            schedulerRepository.save(jobSchedulerDetail);
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {}", jobSchedulerDetailsDTO.getJobName(), e);
            result = false;
        }
       return result;
    }

    @Override
    public boolean pauseJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO) {
        try {
            schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobSchedulerDetailsDTO.getJobName(), jobSchedulerDetailsDTO.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", jobSchedulerDetailsDTO.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean resumeJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO) {
        try {
            schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobSchedulerDetailsDTO.getJobName(), jobSchedulerDetailsDTO.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", jobSchedulerDetailsDTO.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean startJobNow(JobSchedulerDetailsDTO jobSchedulerDetailsDTO) {
        try {
            schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobSchedulerDetailsDTO.getJobName(), jobSchedulerDetailsDTO.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to start new job - {}", jobSchedulerDetailsDTO.getJobName(), e);
            return false;
        }
    }

    public JobSchedulerDetailsDTO findJobByJobName(String jobName) {

        return null;
    }
}
