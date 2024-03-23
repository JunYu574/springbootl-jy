package com.jy.attack.service;

import com.jy.attack.entity.Chapter;
import com.jy.attack.vo.ChapterQuery;
import com.jy.attack.vo.EnemyNumVO;
import com.jy.common.basic.service.BaseService;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:35
 * @Description:
 * @Version: V1.0.0
 */
public interface ChapterService extends BaseService<Chapter, Long> {

    /**
     * 查询列表
     * @param query
     * @return
     */
    List<Chapter> listByQuery(ChapterQuery query);

    /**
     * 查询当前页数据
     * @param query
     * @return
     */
    List<Chapter> pageByQuery(ChapterQuery query);

    /**
     * 查询数量
     * @param query
     * @return
     */
    long countByQuery(ChapterQuery query);

    /**
     * 各类章节关卡怪物数量显示
     * @param category
     * @return
     */
    List<EnemyNumVO> enemyNumByCategory(String category);
}
