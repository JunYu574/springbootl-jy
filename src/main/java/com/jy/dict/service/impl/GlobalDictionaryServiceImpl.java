package com.jy.dict.service.impl;

import com.google.common.collect.Maps;
import com.jy.common.basic.service.impl.BaseServiceImpl;
import com.jy.dict.dao.GlobalDictionaryRepository;
import com.jy.dict.entity.GlobalDictionary;
import com.jy.dict.service.GlobalDictionaryService;
import com.jy.dict.vo.DictQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class GlobalDictionaryServiceImpl extends BaseServiceImpl<GlobalDictionary, Long> implements GlobalDictionaryService {

    @Autowired
    private GlobalDictionaryRepository sysDictRepository;

    @Override
    public List<GlobalDictionary> listByQuery(DictQuery query) {
        return sysDictRepository.queryForList(addDefaultConditions(convert(query)));
    }

    @Override
    public List<GlobalDictionary> pageByQuery(DictQuery query) {
        return sysDictRepository.queryForList(addDefaultConditions(convert(query)), getPage(query.getPage(), query.getLimit(), Sort.by(Sort.Direction.ASC,"sort")));
    }

    @Override
    public long countByQuery(DictQuery query) {
        return sysDictRepository.queryForCount(addDefaultConditions(convert(query)));
    }

    @Override
    public List<GlobalDictionary> getAllGlobalDictionaryInfo() {
        return sysDictRepository.queryForList(addDefaultConditions(Maps.newHashMap()));
    }

    @Override
    public GlobalDictionary findDictByCode(String dictCode) {
        //查询条件
        Map<String, Object> params = Maps.newHashMap();
        params.put("EQ(dictCode)", dictCode);
        List<GlobalDictionary> globalDictionaryList = sysDictRepository.queryForList(addDefaultConditions(params));
        return globalDictionaryList.size() > 0 ? globalDictionaryList.get(0) : null;
    }

    public Map<String, Object> convert(DictQuery query){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(query.getDictName())){
//            params.put("like(dictName)", "%" + query.getDictName() + "%");
            params.put("sqlstring", "(dictName like '%" + query.getDictName() + "%' or dictCode like '%" + query.getDictName() + "%')");
        }
        if(StringUtils.isNotBlank(query.getDictType())){
            params.put("like(dictType)", "%" + query.getDictType() + "%");
        }
        return params;
    }
}
