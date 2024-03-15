package com.jy.dict.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:20
 * @Description:
 * @Version: V1.0.0
 */
@Data
@Entity
@Table(name = "conf_dictionary")
public class GlobalDictionary extends BaseEntity {

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
     * 字典类型
     */
    @Column(name = "dict_type", length = 100)
    private String dictType;

    /**
     * 字典类型名称
     */
    @Transient
    private String dictTypeName;

    /**
     * 字典描述
     */
    @Column(name = "dict_description", length = 200)
    private String dictDescription;

    /**
     * 字典子项集合
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "dict", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "IS_DELETED = 0")
    @OrderBy("sort asc")
    private List<GlobalDictionarySub> subDicts;

}
