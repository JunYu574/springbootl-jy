package com.jy.attack.entity;

import com.jy.common.basic.entiry.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:21
 * @Description:
 * @Version: V1.0.0
 */
@Data
@Entity
@Table(name = "levels")
public class Levels extends BaseEntity {

    /**
     * 关卡数
     */
    @Column(name = "gq_num", length = 4)
    private String gqNum;

    /**
     * 关卡名称
     */
    @Column(name = "gq_name", length = 100)
    private String gqName;

    /**
     * 敌人数量
     */
    @Column(name = "enemy_num", length = 100)
    private String enemyNum;

    /**
     * 所属章节
     */
    @Column(name = "chapter_id", length = 10)
    private Long chapterId;

}
