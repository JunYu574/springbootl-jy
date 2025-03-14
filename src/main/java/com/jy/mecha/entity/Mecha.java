package com.jy.mecha.entity;

import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author: JunYu
 * @Date: 2024/3/15 11:09
 * @Description:
 * @Version: V1.0.0
 */
@Data
@Entity
@Table(name = "mecha")
public class Mecha extends BaseEntity {

    /**
     * 机体名称
     */
    @Column(name = "name", length = 32)
    private String name;

    /**
     * 制造商
     * 字典：MECHA_INFLUENCE_TYPE
     */
    @Column(name = "influence", length = 10)
    private String influence;

    @Transient
    private String influenceName;

    /**
     * 形态
     * 字典：MECHA_SHAPE_TYPE
     */
    @Column(name = "shape", length = 10)
    private String shape;

    @Transient
    private String shapeName;

    /**
     * 稀有度
     * 字典：MECHA_RARITY_TYPE
     */
    @Column(name = "rarity", length = 10)
    private String rarity;

    @Transient
    private String rarityName;

    /**
     * 品质
     * 字典：MECHA_QUALITY_TYPE
     */
    @Column(name = "quality", length = 10)
    private String quality;

    @Transient
    private String qualityName;

    /**
     * 作战距离
     * 字典：MECHA_COMBAT_TYPE
     */
    @Column(name = "combat", length = 10)
    private String combat;

    @Transient
    private String combatName;

    /**
     * 简介
     */
    @Column(name = "synopsis", length = 1000)
    private String synopsis;

    /**
     * 耐久
     */
    @Column(name = "blood", length = 20)
    private String blood;

    /**
     * 质量
     */
    @Column(name = "weight", length = 20)
    private String weight;

    /**
     * 尺寸
     */
    @Column(name = "sized", length = 20)
    private String sized;

    /**
     * 速度
     */
    @Column(name = "speed", length = 20)
    private String speed;
}
