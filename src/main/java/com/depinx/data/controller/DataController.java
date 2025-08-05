package com.depinx.data.controller;


import com.depinx.data.constants.CommonConstants;
import com.depinx.data.constants.ExceptionCode;
import com.depinx.data.entity.AjaxResult;
import com.depinx.data.entity.DataEntity;
import com.depinx.data.entity.LoginRequest;
import com.depinx.data.entity.QueryRequest;
import com.depinx.data.exception.ServiceException;
import com.depinx.data.service.AuthService;
import com.depinx.data.service.CexService;
import com.depinx.data.service.LarkService;
import com.depinx.data.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.depinx.data.entity.AjaxResult.success;

@Slf4j
@RestController
@RequestMapping("")
public class DataController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AuthService authService;
    @Autowired
    private LarkService larkService;
    @Autowired
    private CexService cexService;


    @GetMapping("/public/get/message/{address}")
    public AjaxResult message(@PathVariable("address") String address)
    {
        return success(loginService.message(address));
    }

    @PostMapping("/public/login")
    public AjaxResult signatureVerify(@RequestBody LoginRequest loginRequest) {
        log.info("login data: {}", loginRequest);
        if (loginRequest.getAddress() == null || loginRequest.getAddress().isEmpty()) {
            throw new ServiceException(ExceptionCode.ADDRESS_IS_NULL);
        }
        if (loginRequest.getSign() == null || loginRequest.getSign().isEmpty()) {
            throw new ServiceException(ExceptionCode.SIGN_IS_NULL);
        }

        if (loginService.signVerify(loginRequest.getAddress(), loginRequest.getSign())) {
            log.info("{} 签名验证成功", loginRequest.getAddress());
        }

        AjaxResult ajax = AjaxResult.success();
        String token = authService.login(loginRequest.getAddress());
        log.info("token: {}", token);
        ajax.put(CommonConstants.TOKEN, token);
        return ajax;
    }

    @GetMapping("/public/refreshLark")
    public AjaxResult refreshLark() {
        larkService.fetchLark();
        return success();
    }

    @GetMapping("/public/coinList")
    public AjaxResult coinList() {
        return success(larkService.getCoinList());
    }

    @PostMapping("/public/query")
    public AjaxResult query(@RequestBody QueryRequest queryRequest) {
        return success(cexService.getDataEntity(queryRequest));
    }
}
