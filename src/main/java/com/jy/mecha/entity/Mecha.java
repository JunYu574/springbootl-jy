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
     * 名称
     */
    @Column(name = "name", length = 32)
    private String name;

    /**
     * 势力
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
     * 射程
     * 字典：MECHA_COMBAT_TYPE
     */
    @Column(name = "combat", length = 10)
    private String combat;

    @Transient
    private String combatName;

}
