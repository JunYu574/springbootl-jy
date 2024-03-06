package com.jy.common.basic.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/4 12:03
 * @Description:
 * @Version: V1.0.0
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * 获取数据库类型
     * @return
     */
    int getDbType();

    /**
     * 获取当前repository的class类型
     * @return
     */
    Class<T> getDomainClass();

    /**
     * 根据条件查询数据条数
     * @param dslParamMap
     * @return
     */
    long queryForCount(Map<String, Object> dslParamMap);

    /**
     * 根据DSL参数查询实体信息
     * @param dslParamMap 参数信息
     * @return
     */
    List<T> queryForList(Map<String, Object> dslParamMap);

    /**
     * 根据DSL信息查询实体信息，支持排序
     * @param dslParamMap 参数信息
     * @param sort 排序信息
     * @return
     */
    List<T> queryForList(Map<String, Object> dslParamMap, Sort sort);

    /**
     * 根据DSL信息查询实体信息，支持分页
     * @param dslParamMap 参数信息
     * @param pageable 分页信息
     * @return
     */
    Page<T> queryForPage(Map<String, Object> dslParamMap, Pageable pageable);

    /**
     * 根据DSL参数查询实体信息
     * @param dslParamMap 参数信息
     * @return
     */
    T queryForSingle(Map<String, Object> dslParamMap);

    /**
     * 根据sql语句进行分页查询
     * @param sql SQL语句
     * @param pageable 分页信息
     * @return
     */
    Page<T> queryForPage(String sql, Pageable pageable);
}
