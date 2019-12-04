/*
 *
 *  * Copyright (c) 2019 MK Group.
 *  * All Rights Reserved.
 *  *
 *  * The information specified here is confidential and remains property of the MK Group.
 *
 *
 */

package com.mohan.springboot.app.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MOHANKUMAR
 */
@Getter
@Setter
public class JobSchedulerDetailsDTO implements Serializable {
    private Long id;
    private String jobName;
    private String jobGroup;
    private String jobClass;
    private String jobStatus;
    private String frequency;
    private Long priority;
    private Date startDate;
}
