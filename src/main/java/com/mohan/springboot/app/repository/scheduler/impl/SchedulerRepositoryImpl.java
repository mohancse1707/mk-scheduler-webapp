/*
 *
 *  * Copyright (c) 2019 MK Group.
 *  * All Rights Reserved.
 *  *
 *  * The information specified here is confidential and remains property of the MK Group.
 *
 *
 */

package com.mohan.springboot.app.repository.scheduler.impl;

import com.mohan.springboot.app.entity.scheduler.JobSchedulerDetails;
import com.mohan.springboot.app.repository.scheduler.CustomSchedulerRepository;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MOHANKUMAR
 */
@Slf4j
public class SchedulerRepositoryImpl implements CustomSchedulerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<JobSchedulerDetailsDTO> fetchAllJobSchedulers(String jobName, Long priority, int pageIndex, int pageSize) {

        List<JobSchedulerDetailsDTO> jobSchedulerDetailsDTOS = new ArrayList<>();
        Map<String,Object> parametersMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select jd from JobSchedulerDetails jd ");
        dynamicQueryBuilder(jobName,priority,parametersMap, whereClause, queryBuilder);
        queryBuilder.append(" order by jd.priority");
        log.info("queryBuilder {}", queryBuilder.toString());

        Query filterQuery = getQuery(parametersMap, queryBuilder);

        filterQuery.setFirstResult(pageIndex);
        filterQuery.setMaxResults(pageSize);
        List<JobSchedulerDetails> jobSchedulerDetails  = filterQuery.getResultList();
        jobSchedulerDetails.forEach(job -> {
            JobSchedulerDetailsDTO jobDTO = new JobSchedulerDetailsDTO();
            BeanUtils.copyProperties(job, jobDTO);
            jobSchedulerDetailsDTOS.add(jobDTO);
        });

        return jobSchedulerDetailsDTOS;
    }

    @Override
    public int fetchJobSchedulersCount(String jobName, Long priority) {
        Long count = 0l;
        Map<String,Object> parametersMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(jd) from JobSchedulerDetails jd ");

        dynamicQueryBuilder(jobName, priority, parametersMap, whereClause, queryBuilder);
        log.info("queryBuilder count {}", queryBuilder.toString());
        try {
            Query filterQuery = getQuery(parametersMap, queryBuilder);
            count = (Long) filterQuery.getSingleResult();
            log.info("fetchJobSchedulersCount count = {}",count);
        } catch (HibernateException | DataAccessException e) {
            log.error("Error in method fetchJobSchedulersCount :: {}", e.getMessage());
        }
        return count.intValue();
    }

    private void dynamicQueryBuilder(String jobName, Long priority, Map<String, Object> parametersMap, List<String> whereClause, StringBuilder queryBuilder) {
        if(!StringUtils.isEmpty(jobName)){
            whereClause.add(" LOWER(jd.jobName) like LOWER(:jobName) ");
            parametersMap.put("jobName", "%"+jobName+"%");
        }

        if(isNotNull(priority)){
            whereClause.add("jd.priority = :priority");
            parametersMap.put("priority", priority);
        }

        if(!whereClause.isEmpty()) {
            queryBuilder.append(" where ").append(StringUtils.join(whereClause, " and "));
        }
    }

    private Query getQuery(Map<String, Object> parametersMap, StringBuilder queryBuilder) {
        String query = queryBuilder.toString();
        Query filterQuery = entityManager.createQuery(query);//NO SONAR

        for(Map.Entry<String, Object> entrySet:parametersMap.entrySet()) {
            filterQuery.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        return filterQuery;
    }

    private boolean isNotNull(Long id){
        return (id != null);
    }
}
