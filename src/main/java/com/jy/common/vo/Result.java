package com.jy.common.vo;

import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/2/22 20:39
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class Result<T> {

    private Result(){}

    private Result(Integer code, String message, T data, Long count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    private Integer code;   //返回码，0成功
    private String message; //返回描述
    private T data;         //返回数据
    private Long count;     //分页查询总记录数

    public static Result<Object> success(){
        return new Result(0,"success",null,null);
    }

    public static Result<Object> success(String message){
        return new Result(0,message,null,null);
    }

    public static Result<Object> success(Object data, Long count){
        return new Result(0,"success",data,count);
    }

    public static Result<Object> fail(){
        return new Result(-1,"失败",null,null);
    }

    public static Result<Object> fail(String message){
        return new Result(-1,message,null,null);
    }
}
