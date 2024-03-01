package com.jy.common.exception;

import com.jy.common.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: JunYu
 * @Date: 2024/3/1 19:13
 * @Description:
 * @Version: V1.0.0
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Object> myHandler(Exception e){
        return Result.fail("系统错误：" + e.getMessage());
    }

}
