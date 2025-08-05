package com.depinx.data.service;

import com.depinx.data.constants.CommonConstants;
import com.depinx.data.constants.ExceptionCode;
import com.depinx.data.exception.ServiceException;
import com.depinx.data.utils.MetaMaskUtils;
import com.depinx.data.utils.RandomUtils;
import com.depinx.data.utils.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginService
{

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    public String message(String address) {
        if(StringUtils.isEmpty(address)){
            log.info("metamask get address is null");

        }
        String code = RandomUtils.generateSimpleString(12);
        log.info("metamask code: {}", code);
        redisTemplate.opsForValue().set(CommonConstants.METAMASK_SIGN_PRE + MetaMaskUtils.toUpper(address), code);
        return String.format(MetaMaskUtils.MESSAGE_PRE, code);
    }

    public boolean signVerify(String address, String sign) {
        String key = CommonConstants.METAMASK_SIGN_PRE + MetaMaskUtils.toUpper(address);
        Object codeCache = redisTemplate.opsForValue().get(key);
        String code;
        if(codeCache == null){
            log.info("address {} code is null or invalid!", address);
            throw new ServiceException(ExceptionCode.VALIDATE_MESSAGE_VALIDATE);
        }else {
            code = codeCache.toString();
        }
        log.info("sign address: {}, code: {}", address, code);
        String message = String.format(MetaMaskUtils.MESSAGE_PRE, code);
        log.info("verify address: {}, message: {}, sign: {}",  address,message, sign);
        boolean validate =false;
        try{
            validate = MetaMaskUtils.validate(sign, message, address);
            if(!validate){
                throw new ServiceException(ExceptionCode.VALIDATE_MESSAGE_VALIDATE);
            }
        }catch (Exception e){
            log.info("signVerify address:{},sign:{} validate error",address,sign,e);
        }finally {
            //失效key
            redisTemplate.opsForValue().getAndDelete(key);
        }
        return validate;
    }

}
