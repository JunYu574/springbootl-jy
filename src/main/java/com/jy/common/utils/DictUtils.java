package com.jy.common.utils;

import com.jy.common.vo.DictModel;
import com.jy.dict.entity.GlobalDictionary;
import com.jy.dict.entity.GlobalDictionarySub;
import com.jy.dict.service.GlobalDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:52
 * @Description:
 * @Version: V1.0.0
 */
@Component
public class DictUtils {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    RedisUtil redisUtil;
    @Resource
    private GlobalDictionaryService globalDictionaryService;

    /**
     * 查询字典内容
     *
     * @return
     */
    public List<DictModel> queryList(String dictCode) {
        GlobalDictionary dictInfo = globalDictionaryService.findDictByCode(dictCode);
        if (dictInfo == null) {
            logger.error("字典代码存在问题，未查询到对应的字典信息");
            return null;
        } else {
            List<DictModel> dictsModels = new ArrayList<>();
            for (GlobalDictionarySub sub : dictInfo.getSubDicts()) {
                DictModel model = new DictModel();
                model.setId(sub.getId());
                model.setKey(sub.getDictCode());
                model.setValue(sub.getDictName());
                dictsModels.add(model);
            }
            return dictsModels;
        }
    }

    /**
     * 不使用缓存，直接查询字典
     *
     * @param dictCode
     * @return
     */
    public Map<String, String> loadDictMap(String dictCode) {

        GlobalDictionary dictInfo = globalDictionaryService.findDictByCode(dictCode);
        LinkedHashMap<String, String> dictMap = new LinkedHashMap<>();
        if (dictInfo == null) {
            logger.error("字典代码存在问题，未查询到对应的字典信息");
            return null;
        } else {
            for (GlobalDictionarySub subdict : dictInfo.getSubDicts()) {
                dictMap.put(subdict.getDictCode(), subdict.getDictName());
            }
        }
        return dictMap;
    }

    /**
     * 缓存字典信息
     */
    public void cacheDictionary() {
        redisUtil.del("IsCacheDict");
        if (!redisUtil.containsKey("IsCacheDict")) {
            List<GlobalDictionary> globalDictionaries = globalDictionaryService.getAllGlobalDictionaryInfo();
            /**
             * 迭代
             */
            logger.debug("共缓存{}项", globalDictionaries.size());
            for (GlobalDictionary dict : globalDictionaries) {
                List<GlobalDictionarySub> subDicts = dict.getSubDicts();
                if (subDicts == null) {
                    continue;
                }
                Map<Object, Object> dictMap = new LinkedHashMap<>();
                for (GlobalDictionarySub subdict : subDicts) {
                    dictMap.put(subdict.getDictCode(), subdict.getDictName());
                }
                redisUtil.cacheValue("IsCacheDict", true, 604800);
                redisUtil.del(dict.getDictCode());
                redisUtil.hmset(dict.getDictCode(), dictMap);
            }
        }
    }


}
