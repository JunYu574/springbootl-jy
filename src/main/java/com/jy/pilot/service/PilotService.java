package com.jy.pilot.service;

import com.jy.common.basic.service.BaseService;
import com.jy.pilot.entity.Pilot;
import com.jy.pilot.vo.PilotQuery;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 12:53
 * @Description:
 * @Version: V1.0.0
 */
public interface PilotService extends BaseService<Pilot, Long> {

    /**
     * 查询列表
     * @param query
     * @return
     */
    List<Pilot> listByQuery(PilotQuery query);

    /**
     * 查询当前页数据
     * @param query
     * @return
     */
    List<Pilot> pageByQuery(PilotQuery query);

    /**
     * 查询数量
     * @param query
     * @return
     */
    long countByQuery(PilotQuery query);
}
