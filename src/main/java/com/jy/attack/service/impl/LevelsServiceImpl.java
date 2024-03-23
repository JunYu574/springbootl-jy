package com.jy.attack.service.impl;

import com.jy.attack.dao.LevelsRepository;
import com.jy.attack.entity.Levels;
import com.jy.attack.service.LevelsService;
import com.jy.common.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:36
 * @Description:
 * @Version: V1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LevelsServiceImpl extends BaseServiceImpl<Levels, Long> implements LevelsService {

    @Autowired
    private LevelsRepository levelsRepository;

    @Override
    public List<Levels> listByChapterId(Long chapterId) {
        Map<String, Object> params = new HashMap<>();
        params.put("EQ(chapterId)", chapterId);
        return levelsRepository.queryForList(addDefaultConditions(params));
    }

    @Override
    public Long countByChapterId(Long chapterId) {
        Map<String, Object> params = new HashMap<>();
        params.put("EQ(chapterId)", chapterId);
        return levelsRepository.queryForCount(addDefaultConditions(params));
    }

    @Override
    public void deleteByChapterId(Long chapterId) {
        levelsRepository.deleteByChapterId(chapterId);
    }
}
