package com.jy.dict.dao;

import com.jy.common.basic.dao.BaseRepository;
import com.jy.dict.entity.GlobalDictionary;
import org.springframework.stereotype.Repository;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:24
 * @Description:
 * @Version: V1.0.0
 */
@Repository
public interface GlobalDictionaryRepository extends BaseRepository<GlobalDictionary, Long> {
}
