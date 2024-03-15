package com.jy.mecha.vo;

import com.jy.common.vo.Page;
import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/3/15 11:15
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class MechaQuery extends Page {

    /** 机体 */
    private String name;
    /** 势力 */
    private String influence;
    /** 形态 */
    private String shape;
    /** 稀有度 */
    private String rarity;
    /** 品质 */
    private String quality;
    /** 射程 */
    private String combat;
}
