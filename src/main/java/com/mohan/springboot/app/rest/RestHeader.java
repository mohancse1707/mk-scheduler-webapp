/*
 *
 *  * Copyright (c) 2019 MK Group.
 *  * All Rights Reserved.
 *  *
 *  * The information specified here is confidential and remains property of the MK Group.
 *
 *
 */

package com.mohan.springboot.app.rest;
import java.io.Serializable;

/**
 * @author MOHANKUMAR
 */
public class RestHeader implements Serializable {
    private static final long serialVersionUID = 4435221932867621315L;
    private Long totalCount;
    private Long pageSize;
    private Long pageIndex;
    private String message;

    public RestHeader(Long totalCount, Long pageSize, Long pageIndex, String message) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.message = message;
    }

    public RestHeader(int totalCount, int pageSize, int pageIndex, String message) {
        this.totalCount = Long.valueOf(totalCount);
        this.message = message;
        this.pageIndex = Long.valueOf(pageIndex);
        this.pageSize = Long.valueOf(pageSize);
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
