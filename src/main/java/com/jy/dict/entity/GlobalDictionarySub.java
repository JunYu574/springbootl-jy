package com.jy.dict.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:28
 * @Description:
 * @Version: V1.0.0
 */
@Data
@Entity
@Table(name = "conf_dictionary_sub")
public class GlobalDictionarySub extends BaseEntity {

    /**
     * 字典代码
     */
    @Column(name = "dict_code", length = 100)
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name", length = 100)
    private String dictName;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled", nullable = false)
    private boolean enabled = true;

    /**
     * 字典父类
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "dict_id")
    private GlobalDictionary dict;

    @Override
    public String toString() {
        return "GlobalDictionarySub{" +
                "dictCode='" + dictCode + '\'' +
                ", dictName='" + dictName + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
