package com.jy.common.vo;

import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 21:24
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class DictModel {

    /**
     * 主键
     */
    private Long id;

    /**
     * 存储的键
     */
    private String key;

    /**
     * 存储的值
     */
    private String value;

}
