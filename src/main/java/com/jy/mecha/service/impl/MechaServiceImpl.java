package com.jy.mecha.service.impl;

import com.jy.common.basic.service.impl.BaseServiceImpl;
import com.jy.mecha.dao.MechaRepository;
import com.jy.mecha.entity.Mecha;
import com.jy.mecha.service.MechaService;
import com.jy.mecha.vo.MechaQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/15 11:12
 * @Description:
 * @Version: V1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MechaServiceImpl extends BaseServiceImpl<Mecha, Long> implements MechaService {

    @Autowired
    private MechaRepository mechaRepository;

    @Override
    public List<Mecha> listByQuery(MechaQuery query) {
        return mechaRepository.queryForList(addDefaultConditions(convert(query)));
    }

    @Override
    public List<Mecha> pageByQuery(MechaQuery query) {
        return mechaRepository.queryForList(addDefaultConditions(convert(query)), getPage(query.getPage(), query.getLimit(), Sort.by(Sort.Direction.ASC,"sort")));
    }

    @Override
    public long countByQuery(MechaQuery query) {
        return mechaRepository.queryForCount(addDefaultConditions(convert(query)));
    }

    public Map<String, Object> convert(MechaQuery query){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(query.getName())){
            params.put("like(name)", "%" + query.getName() + "%");
        }
        if(StringUtils.isNotBlank(query.getInfluence())){
            params.put("EQ(influence)", query.getInfluence());
        }
        if(StringUtils.isNotBlank(query.getShape())){
            params.put("EQ(shape)", query.getShape());
        }
        if(StringUtils.isNotBlank(query.getRarity())){
            params.put("EQ(rarity)", query.getRarity());
        }
        if(StringUtils.isNotBlank(query.getQuality())){
            params.put("EQ(quality)", query.getQuality());
        }
        if(StringUtils.isNotBlank(query.getCombat())){
            params.put("EQ(combat)", query.getCombat());
        }
        return params;
    }

}
