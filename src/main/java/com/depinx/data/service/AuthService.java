package com.depinx.data.service;

import com.depinx.data.constants.CommonConstants;
import com.depinx.data.exception.ServiceException;
import com.depinx.data.security.AsyncManager;
import com.depinx.data.security.AuthenticationContextHolder;
import com.depinx.data.security.LoginUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class AuthService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TokenService tokenService;


    public String login(String userName)
    {
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, "NO_PASSWORD");
            AuthenticationContextHolder.setContext(authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e)
        {
            throw new ServiceException(e.getMessage());
        }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(loginUser);
    }

}
