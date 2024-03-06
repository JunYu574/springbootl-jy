package com.jy.pilot.dao;

import com.jy.common.basic.dao.BaseRepository;
import com.jy.pilot.entity.Pilot;
import org.springframework.stereotype.Repository;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 12:57
 * @Description:
 * @Version: V1.0.0
 */
@Repository
public interface PilotRepository extends BaseRepository<Pilot, Long> {
}
