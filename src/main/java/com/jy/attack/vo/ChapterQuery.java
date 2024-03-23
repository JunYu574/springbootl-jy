package com.jy.attack.vo;

import com.jy.common.vo.Page;
import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:49
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class ChapterQuery extends Page {

    /** 章节数 */
    private String zjNum;
    /** 章节名称 */
    private String zjName;
    /** 章节类别 */
    private String category;

}
