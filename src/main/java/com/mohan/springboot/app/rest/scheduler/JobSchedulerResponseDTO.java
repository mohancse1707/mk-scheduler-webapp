/*
 *
 *  * Copyright (c) 2019 MK Group.
 *  * All Rights Reserved.
 *  *
 *  * The information specified here is confidential and remains property of the MK Group.
 *
 *
 */

package com.mohan.springboot.app.rest.scheduler;

import com.mohan.springboot.app.rest.RestHeader;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author MOHANKUMAR
 */
@Getter
@Setter
public class JobSchedulerResponseDTO implements Serializable {

    private List<JobSchedulerDetailsDTO> jobSchedulerDetailsDTOList;
    private RestHeader restHeader;
    private int totalItems;
    private boolean result;

}
