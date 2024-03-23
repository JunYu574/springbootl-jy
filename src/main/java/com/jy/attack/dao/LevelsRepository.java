package com.jy.attack.dao;

import com.jy.attack.entity.Levels;
import com.jy.common.basic.dao.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:39
 * @Description:
 * @Version: V1.0.0
 */
@Repository
public interface LevelsRepository extends BaseRepository<Levels, Long> {

    @Modifying
    @Query("update Levels l set l.deleted = 1 where l.chapterId = :chapterId")
    void deleteByChapterId(@Param("chapterId") Long chapterId);

}
