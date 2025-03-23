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
     * 角色名
     */
    @Column(name = "name", length = 32)
    private String name;

    /**
     * 剧情专机
     */
    @Column(name = "drama_plane", length = 32)
    private String dramaPlane;

    /**
     * 势力
     * 字典：PILOT_INFLUENCE_TYPE
     */
    @Column(name = "influence", length = 10)
    private String influence;

    /**
     * 势力名称
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
     * 字典：PILOT_DISPOSITION_TYPE
     */
    @Column(name = "disposition", length = 32)
    private String disposition;

    /**
     * 性格
     */
    @Transient
    private String dispositionName;

    /**
     * 简介
     */
    @Column(name = "synopsis", length = 1000)
    private String synopsis;

    /**
     * 射击
     */
    @Column(name = "shoot", length = 20)
    private String shoot;

    /**
     * 格斗
     */
    @Column(name = "grapple", length = 20)
    private String grapple;

    /**
     * 防御
     */
    @Column(name = "defense", length = 20)
    private String defense;

    /**
     * 反应
     */
    @Column(name = "response", length = 20)
    private String response;

    /**
     * 可用副官位
     */
    @Column(name = "available_aide", length = 40)
    private String availableAide;

    /**
     * 可用副官位
     * 字典：PILOT_SKILL_TYPE
     */
    @Transient
    private String availableAideName;

    /**
     * 是否觉醒
     */
    @Column(name = "is_awakening", nullable = false)
    private boolean awakening;

}
