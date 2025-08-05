package com.depinx.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataEntity {
    private String projectName;
    private String coinName;
    private String bnMa;
    private String bnTwap;
    private String okxMa;
    private String okxTwap;
    private String price;

}
