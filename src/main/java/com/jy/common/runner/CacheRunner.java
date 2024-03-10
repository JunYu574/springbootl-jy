package com.jy.common.runner;

import com.jy.common.utils.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:51
 * @Description: 启动时获取字典缓存
 * @Version: V1.0.0
 */
@Slf4j
@Component
public class CacheRunner implements CommandLineRunner {

    @Resource
    DictUtils dictUtils;

    @Override
    public void run(String... args) throws Exception {
        log.info(">>>>>>>>>>>>>>>服务启动执行，执行加载数据字典缓存 <<<<<<<<<<<<<");
        dictUtils.cacheDictionary();
    }
}
