package com.jy.common.cache;

import com.jy.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 13:06
 * @Description:
 * @Version: V1.0.0
 */
@Component
public class CacheDict {

    @Autowired
    private RedisUtil redisUtil;

    private static RedisUtil redisUtilStatic;

    @PostConstruct
    public void init() {
        setRedisUtilStatic(redisUtil);
    }

    public static synchronized void setRedisUtilStatic(RedisUtil redisUtil){
        redisUtilStatic = redisUtil;
    }

    /**
     * 类型与字典项  由List 转成Map（key,value）
     */
    public static class dictMap{

        private dictMap() {
            throw new IllegalStateException("dictMap");
        }

        public static Map<String, String> get(String dictCode){
            Map<Object, Object> dictMap = redisUtilStatic.hmget(dictCode);
            Map<String, String> dictMapString = dictMap.entrySet().stream().collect(Collectors.toMap(o -> (String)o.getKey(), o -> (String)o.getValue()));
            Collator collator = Collator.getInstance();
            Map<String, String> sortedMap = new TreeMap<>((Comparator<Object>) (element1, element2) -> {
                CollationKey key1 = null;
                CollationKey key2 = null;
                try {
                    Integer int1 = Integer.parseInt(String.valueOf(element1));
                    Integer int2 = Integer.parseInt(String.valueOf(element2));
                    return int1.compareTo(int2);
                } catch (Exception e) {
                    key1 = collator.getCollationKey(element1.toString().toLowerCase());
                    key2 = collator.getCollationKey(element2.toString().toLowerCase());
                }
                return key1.compareTo(key2);
            });
            sortedMap.putAll(dictMapString);
            return sortedMap;
        }

        /**
         * 按照sort排序取字典
         * @param dictCode
         * @return
         */
        public static Map<String, String> getMap(String dictCode){
            Map<Object, Object> dictMap = redisUtilStatic.hmget(dictCode);
            Map<String, String> dictMapString = new LinkedHashMap<>();
            for(Object key : dictMap.keySet()){
                dictMapString.put(String.valueOf(key),String.valueOf(dictMap.get(key)));
            }
            return dictMapString;
        }
    }
}
