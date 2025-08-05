package com.depinx.data.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String address;
    private String sign;
}
