package com.jy.attack.service;

import com.jy.attack.entity.Levels;
import com.jy.common.basic.service.BaseService;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:36
 * @Description:
 * @Version: V1.0.0
 */
public interface LevelsService extends BaseService<Levels, Long> {

    /**
     * 根据章节Id查询其下关卡列表
     * @param chapterId 章节Id
     * @return
     */
    List<Levels> listByChapterId(Long chapterId);

    /**
     * 根据章节Id查询其下关卡数量
     * @param chapterId
     * @return
     */
    Long countByChapterId(Long chapterId);

    /**
     * 根据章节Id逻辑删除其下关卡
     * @param chapterId
     * @return
     */
    void deleteByChapterId(Long chapterId);
}
