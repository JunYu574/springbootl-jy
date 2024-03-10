package com.jy.dict.vo;

import com.jy.common.vo.Page;
import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 19:56
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class DictQuery extends Page {

    /** 字典类型 */
    private String dictType;
    /** 字典名称 */
    private String dictName;

}
