package com.jy.pilot.entity;

import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    /** 名称 */
    @Column(name = "name")
    private String name;

    /** 势力 */
    @Column(name = "influence")
    private String influence;

    /** 性别 */
    @Column(name = "sex")
    private String sex;

}
