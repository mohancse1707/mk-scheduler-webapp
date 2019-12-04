/*
 *
 *  * Copyright (c) 2019. MK Groups.
 *  * All rights reserved.
 *  * All data of MK groups are confidential.
 *
 */

package com.mohan.springboot.app.entity.scheduler;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author MOHANKUMAR
 */
@Getter
@Setter
@Entity
@Table(name = "JOB_SCHEDULER_DETAILS")
public class JobSchedulerDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JOB_SCHEDULER_ID")
    private Long id;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_GROUP")
    private String jobGroup;

    @Column(name = "JOB_CLASS")
    private String jobClass;

    @Column(name = "JOB_FREQUENCY")
    private String frequency;

    @Column(name = "JOB_PRIORITY")
    private Long priority;

    @Column(name="JOB_START_DATE")
    private Date startDate;

    @Column(name = "JOB_STATUS")
    private String jobStatus;
}
