/*
 *
 *  * Copyright (c) 2019. MK Groups.
 *  * All rights reserved.
 *  * All data of MK groups are confidential.
 *
 */

package com.mohan.springboot.app.service.scheduler;

import com.mohan.springboot.app.entity.scheduler.JobSchedulerDetails;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;

import java.util.List;

/**
 * @author MOHANKUMAR
 */
public interface SchedulerService {

    List<JobSchedulerDetailsDTO> fetchAllJobSchedulers(String jobName, Long priority, int pageIndex, int pageSize);

    int fetchJobSchedulersCount(String jobName, Long priority);

    void startAllSchedulers();

    boolean scheduleNewJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO);

    boolean updateScheduleJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO);

    boolean deleteJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO);

    boolean unScheduleJob(String jobName);

    boolean pauseJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO);

    boolean resumeJob(JobSchedulerDetailsDTO jobSchedulerDetailsDTO);

    boolean startJobNow(JobSchedulerDetailsDTO jobSchedulerDetailsDTO);
}
