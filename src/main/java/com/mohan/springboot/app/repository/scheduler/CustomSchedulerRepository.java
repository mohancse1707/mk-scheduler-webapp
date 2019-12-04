/*
 *
 *  * Copyright (c) 2019 MK Group.
 *  * All Rights Reserved.
 *  *
 *  * The information specified here is confidential and remains property of the MK Group.
 *
 *
 */

package com.mohan.springboot.app.repository.scheduler;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;

import java.util.List;

/**
 * @author MOHANKUMAR
 */
public interface CustomSchedulerRepository {

    List<JobSchedulerDetailsDTO> fetchAllJobSchedulers(String jobName, Long priority, int pageIndex, int pageSize);

    int fetchJobSchedulersCount(String jobName, Long priority);
}
