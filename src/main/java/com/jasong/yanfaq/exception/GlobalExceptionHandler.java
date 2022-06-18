package com.jasong.yanfaq.exception;

import com.jasong.yanfaq.commom.Result;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        return Result.error("500","系统异常");
    }
}
