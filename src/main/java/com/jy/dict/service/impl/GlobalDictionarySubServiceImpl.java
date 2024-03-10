package com.jy.dict.service.impl;

import com.jy.common.basic.service.impl.BaseServiceImpl;
import com.jy.dict.dao.GlobalDictionarySubRepository;
import com.jy.dict.entity.GlobalDictionarySub;
import com.jy.dict.service.GlobalDictionarySubService;
import com.jy.dict.vo.SubDictQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:22
 * @Description:
 * @Version: V1.0.0
 */
@Service
public class GlobalDictionarySubServiceImpl extends BaseServiceImpl<GlobalDictionarySub, Long> implements GlobalDictionarySubService {

    @Autowired
    private GlobalDictionarySubRepository sysSubDictRepository;

    @Override
    public List<GlobalDictionarySub> listByQuery(SubDictQuery query) {
        return sysSubDictRepository.queryForList(addDefaultConditions(convert(query)));
    }

    @Override
    public List<GlobalDictionarySub> pageByQuery(SubDictQuery query) {
        return sysSubDictRepository.queryForList(addDefaultConditions(convert(query)), getPage(query.getPage(), query.getLimit(), null));
    }

    @Override
    public long countByQuery(SubDictQuery query) {
        return sysSubDictRepository.queryForCount(addDefaultConditions(convert(query)));
    }


    public Map<String, Object> convert(SubDictQuery query){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(query.getDictCode())){
            params.put("like(dictCode)", "%" + query.getDictCode() + "%");
        }
        if(StringUtils.isNotBlank(query.getDictName())){
            params.put("like(dictName)", "%" + query.getDictName() + "%");
        }
        if(null != query.getDictId()){
            params.put("EQ(dict.id)", query.getDictId());
        }
        return params;
    }

}
