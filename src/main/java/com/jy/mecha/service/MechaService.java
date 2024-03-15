package com.jy.mecha.service;

import com.jy.common.basic.service.BaseService;
import com.jy.mecha.entity.Mecha;
import com.jy.mecha.vo.MechaQuery;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/15 11:11
 * @Description:
 * @Version: V1.0.0
 */
public interface MechaService extends BaseService<Mecha, Long> {

    /**
     * 查询列表
     * @param query
     * @return
     */
    List<Mecha> listByQuery(MechaQuery query);

    /**
     * 查询当前页数据
     * @param query
     * @return
     */
    List<Mecha> pageByQuery(MechaQuery query);

    /**
     * 查询数量
     * @param query
     * @return
     */
    long countByQuery(MechaQuery query);

}
