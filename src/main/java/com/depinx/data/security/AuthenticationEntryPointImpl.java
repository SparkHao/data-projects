package com.depinx.data.security;

import com.alibaba.fastjson2.JSON;
import com.depinx.data.constants.HttpStatus;
import com.depinx.data.entity.AjaxResult;
import com.depinx.data.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = String.format("request：%s，unauthorized", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}
