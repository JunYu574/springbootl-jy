package com.jy.dict.service;

import com.jy.common.basic.service.BaseService;
import com.jy.dict.entity.GlobalDictionary;
import com.jy.dict.vo.DictQuery;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:22
 * @Description:
 * @Version: V1.0.0
 */
public interface GlobalDictionaryService extends BaseService<GlobalDictionary, Long> {

    /**
     * 查询字典列表
     * @param query
     * @return
     */
    List<GlobalDictionary> listByQuery(DictQuery query);

    /**
     * 查询当前页数据
     * @param query
     * @return
     */
    List<GlobalDictionary> pageByQuery(DictQuery query);

    /**
     * 查询数量
     * @param query
     * @return
     */
    long countByQuery(DictQuery query);

    /**
     * 查找所有GlobalDictionary Order by id
     *
     * @return
     */
    List<GlobalDictionary> getAllGlobalDictionaryInfo();

    /**
     * 根据字典代码查询字典
     *
     * @param dictCode 字典代码
     * @return 返回查询字典对象
     */
    GlobalDictionary findDictByCode(String dictCode);

}
