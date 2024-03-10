package com.jy.dict.service;

import com.jy.common.basic.service.BaseService;
import com.jy.dict.entity.GlobalDictionarySub;
import com.jy.dict.vo.SubDictQuery;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:22
 * @Description:
 * @Version: V1.0.0
 */
public interface GlobalDictionarySubService extends BaseService<GlobalDictionarySub, Long> {

    /**
     * 查询子字典列表
     * @param query
     * @return
     */
    List<GlobalDictionarySub> listByQuery(SubDictQuery query);

    /**
     * 查询当前页数据
     * @param query
     * @return
     */
    List<GlobalDictionarySub> pageByQuery(SubDictQuery query);

    /**
     * 查询数量
     * @param query
     * @return
     */
    long countByQuery(SubDictQuery query);

}
