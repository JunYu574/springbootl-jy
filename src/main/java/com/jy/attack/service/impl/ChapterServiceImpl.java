package com.jy.attack.service.impl;

import com.jy.attack.dao.ChapterRepository;
import com.jy.attack.entity.Chapter;
import com.jy.attack.service.ChapterService;
import com.jy.attack.vo.ChapterQuery;
import com.jy.attack.vo.EnemyNumVO;
import com.jy.common.basic.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Long> implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public List<Chapter> listByQuery(ChapterQuery query) {
        return chapterRepository.queryForList(addDefaultConditions(convert(query)));
    }

    @Override
    public List<Chapter> pageByQuery(ChapterQuery query) {
        return chapterRepository.queryForList(addDefaultConditions(convert(query)), getPage(query.getPage(), query.getLimit(), Sort.by(Sort.Direction.ASC,"sort")));
    }

    @Override
    public long countByQuery(ChapterQuery query) {
        return chapterRepository.queryForCount(addDefaultConditions(convert(query)));
    }

    @Override
    public List<EnemyNumVO> enemyNumByCategory(String category) {
        List<Object> list = chapterRepository.enemyNumByCategory(category);
        List<EnemyNumVO> enemyNumVOS = new ArrayList<>();
        list.stream().map(obj -> (Object[]) obj).forEach(o -> {
            EnemyNumVO vo = new EnemyNumVO();
            if (o[0] != null) {
                vo.setChapterNum("第" + o[0].toString() + "章");
                if (o[1] != null) {
                    String[] enemyNum = o[1].toString().split(",");
                    switch (enemyNum.length) {
                        case 6:
                            vo.setEnemyNum6(enemyNum[5]);
                        case 5:
                            vo.setEnemyNum5(enemyNum[4]);
                        case 4:
                            vo.setEnemyNum4(enemyNum[3]);
                        case 3:
                            vo.setEnemyNum3(enemyNum[2]);
                        case 2:
                            vo.setEnemyNum2(enemyNum[1]);
                        case 1:
                            vo.setEnemyNum1(enemyNum[0]);
                    }
                }
                enemyNumVOS.add(vo);
            }
        });
        return enemyNumVOS;
    }

    public Map<String, Object> convert(ChapterQuery query){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(query.getCategory())){
            params.put("EQ(category)", query.getCategory());
        }
        return params;
    }

}
