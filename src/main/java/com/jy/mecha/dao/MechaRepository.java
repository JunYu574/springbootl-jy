package com.jy.mecha.dao;

import com.jy.common.basic.dao.BaseRepository;
import com.jy.mecha.entity.Mecha;
import org.springframework.stereotype.Repository;

/**
 * @Author: JunYu
 * @Date: 2024/3/15 11:14
 * @Description:
 * @Version: V1.0.0
 */
@Repository
public interface MechaRepository extends BaseRepository<Mecha, Long> {
}
