package com.jy.attack.entity;

import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:18
 * @Description:
 * @Version: V1.0.0
 */
@Data
@Entity
@Table(name = "chapter")
public class Chapter extends BaseEntity {

    /**
     * 章节数
     */
    @Column(name = "zj_num", length = 10)
    private String zjNum;

    /**
     * 章节名称
     */
    @Column(name = "zj_name", length = 100)
    private String zjName;

    /**
     * 章节所属
     */
    @Column(name = "category", length = 32)
    private String category;

}
