package com.depinx.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class QueryRequest {
    private String coinName;
    private Date startTime;
    private Date endTime;
}
