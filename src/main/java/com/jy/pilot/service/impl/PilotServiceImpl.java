package com.jy.pilot.service.impl;

import com.jy.common.basic.service.impl.BaseServiceImpl;
import com.jy.pilot.dao.PilotRepository;
import com.jy.pilot.entity.Pilot;
import com.jy.pilot.service.PilotService;
import com.jy.pilot.vo.PilotQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 12:54
 * @Description:
 * @Version: V1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PilotServiceImpl extends BaseServiceImpl<Pilot, Long> implements PilotService {

    @Autowired
    private PilotRepository pilotRepository;

    @Override
    public List<Pilot> listByQuery(PilotQuery query) {
        return pilotRepository.queryForList(addDefaultConditions(convert(query)));
    }

    @Override
    public List<Pilot> pageByQuery(PilotQuery query) {
        return pilotRepository.queryForList(addDefaultConditions(convert(query)), getPage(query.getPage(), query.getLimit(), null));
    }

    @Override
    public long countByQuery(PilotQuery query) {
        return pilotRepository.queryForCount(addDefaultConditions(convert(query)));
    }

    public Map<String, Object> convert(PilotQuery query){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(query.getName())){
            params.put("like(name)", "%" + query.getName() + "%");
        }
        if(StringUtils.isNotBlank(query.getInfluence())){
            params.put("EQ(influence)", query.getInfluence());
        }
        if(StringUtils.isNotBlank(query.getDisposition())){
            params.put("EQ(disposition)", query.getDisposition());
        }
        return params;
    }
}
