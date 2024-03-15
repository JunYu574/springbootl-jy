package com.jy.pilot.entity;

import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author: JunYu
 * @Date: 2024/3/2 18:59
 * @Description:
 * @Version: V1.0.0
 */
@Data
@Entity
@Table(name = "pilot")
public class Pilot extends BaseEntity {

    /**
     * 名称
     */
    @Column(name = "name", length = 32)
    private String name;

    /**
     * 势力
     */
    @Column(name = "influence", length = 10)
    private String influence;

    /**
     * 势力
     */
    @Transient
    private String influenceName;

    /**
     * 性别
     */
    @Column(name = "sex", length = 10)
    private String sex;

    /**
     * 性格
     */
    @Column(name = "disposition", length = 32)
    private String disposition;

    /**
     * 性格
     */
    @Transient
    private String dispositionName;

    /**
     * 是否觉醒
     */
    @Column(name = "is_awakening", nullable = false)
    private boolean awakenin;

}
