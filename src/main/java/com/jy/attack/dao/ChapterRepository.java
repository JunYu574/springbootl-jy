package com.jy.attack.dao;

import com.jy.attack.entity.Chapter;
import com.jy.common.basic.dao.BaseRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:39
 * @Description:
 * @Version: V1.0.0
 */
@Repository
public interface ChapterRepository extends BaseRepository<Chapter, Long> {

    @Query(value = "SELECT c.zj_num,GROUP_CONCAT(l.enemy_num) " +
            "FROM chapter c " +
            "LEFT JOIN levels l ON c.id = l.chapter_id " +
            "WHERE c.category = :category AND l.enemy_num IS NOT NULL AND c.is_deleted = 0 AND l.is_deleted = 0 " +
            "GROUP BY c.zj_num ", nativeQuery = true)
    List<Object> enemyNumByCategory(@Param("category") String category);
}
