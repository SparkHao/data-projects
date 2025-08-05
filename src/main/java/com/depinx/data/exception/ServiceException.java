package com.depinx.data.exception;

import com.depinx.data.constants.ExceptionCode;
import lombok.Data;

/**
 * 业务异常
 * 
 * @author ruoyi
 */
@Data
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     *
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(ExceptionCode exceptionCode)
    {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMsg();
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

}