package com.jy.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: JunYu
 * @Date: 2024/2/26 19:18
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class Page implements Serializable {

    private Integer page;
    private Integer limit;

    public Long getStart(){
        return (page -1L) * limit;
    }

}
