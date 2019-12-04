/*
 *
 *  * Copyright (c) 2019. MK Groups.
 *  * All rights reserved.
 *  * All data of MK groups are confidential.
 *
 */

package com.mohan.springboot.app.repository.scheduler;

import com.mohan.springboot.app.entity.scheduler.JobSchedulerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MOHANKUMAR
 */
@Repository
public interface SchedulerRepository extends JpaRepository<JobSchedulerDetails,Long>, CustomSchedulerRepository {

}
