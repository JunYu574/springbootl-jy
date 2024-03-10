package com.jy.pilot.vo;

import com.jy.common.vo.Page;
import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 13:13
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class PilotQuery extends Page {
    /** 机师 */
    private String name;
    /** 势力 */
    private String influence;

}
