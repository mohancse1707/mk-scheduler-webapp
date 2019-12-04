/*
 *
 *  * Copyright (c) 2019. MK Groups.
 *  * All rights reserved.
 *  * All data of MK groups are confidential.
 *
 */

package com.mohan.springboot.app.rest.scheduler;


import com.mohan.springboot.app.rest.RestHeader;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mohan.springboot.app.service.scheduler.SchedulerService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author MOHANKUMAR
 */
@RestController
@RequestMapping("/app/scheduler/rest")
public class SchedulerRestController {

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping("/fetchAllJobSchedulers" )
    public ResponseEntity<JobSchedulerResponseDTO>  fetchAllJobSchedulers(@RequestParam(value = "jobName", required = false) String jobName,
                                                             @RequestParam(value = "priority", required = false) Long priority,
                                                             @RequestParam(value = "startIndex", required = false, defaultValue = "1") int startIndex,
                                                             @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
        if (startIndex < 1) {
            startIndex = 1;
        }

        int pageIndex = (startIndex - 1) * pageSize;
        int totalCount = schedulerService.fetchJobSchedulersCount(jobName, priority);
        List<JobSchedulerDetailsDTO> technicalDebtDTOList = schedulerService.fetchAllJobSchedulers(jobName, priority, pageIndex,pageSize);
        RestHeader header = new RestHeader(totalCount, pageSize, startIndex, "");

        JobSchedulerResponseDTO jobSchedulerResponseDTO = new JobSchedulerResponseDTO();
        jobSchedulerResponseDTO.setJobSchedulerDetailsDTOList(technicalDebtDTOList);
        jobSchedulerResponseDTO.setRestHeader(header);
        jobSchedulerResponseDTO.setTotalItems(totalCount);
        return new ResponseEntity<JobSchedulerResponseDTO>(jobSchedulerResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/createOrUpdateScheduler", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean>  createOrUpdateScheduler(@Valid @RequestBody JobSchedulerDetailsDTO schedulerDetailsDTO) {
        Boolean saveOrUpdateStatus = Boolean.FALSE;
        if(schedulerDetailsDTO.getId() == null || schedulerDetailsDTO.getId() == 0l){
            saveOrUpdateStatus = schedulerService.scheduleNewJob(schedulerDetailsDTO);
        } else {
            saveOrUpdateStatus = schedulerService.updateScheduleJob(schedulerDetailsDTO);
        }

        if(saveOrUpdateStatus) {
            return new ResponseEntity<Boolean>(saveOrUpdateStatus, HttpStatus.OK);
        }else{
            return new ResponseEntity<Boolean>(saveOrUpdateStatus, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping(value = "/cancelJobScheduler", produces = MediaType.APPLICATION_JSON_VALUE) // cancel or pause
    public ResponseEntity<Boolean>  cancelJobScheduler(@Valid @RequestBody JobSchedulerDetailsDTO schedulerDetailsDTO) {
        Boolean cancelJobStatus = schedulerService.deleteJob(schedulerDetailsDTO);
        if(cancelJobStatus) {
            return new ResponseEntity<Boolean>(cancelJobStatus, HttpStatus.OK);
        }else{
            return new ResponseEntity<Boolean>(cancelJobStatus, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
