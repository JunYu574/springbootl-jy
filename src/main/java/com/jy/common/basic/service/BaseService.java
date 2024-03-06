package com.jy.common.basic.service;

import com.jy.common.basic.entiry.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/4 11:23
 * @Description:
 * @Version: V1.0.0
 */
public interface BaseService<T extends BaseEntity, ID> {

    /**
     * 保存
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 保存数据集合
     * @param entities
     * @return
     */
    List<T> saveAll(List<T> entities);

    /**
     * 更新
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     * 物理删除对象
     * @param entity
     */
    void deleteItem(T entity);

    /**
     * 物理删除对象列表
     * @param entities
     */
    void deleteItems(List<T> entities);

    /**
     * 根据ID逻辑删除实体信息
     * @param id
     */
    void deleteById(ID id);

    /**
     * 根据ID列表逻辑删除实体信息
     * @param ids 实体对象数组列表
     */
    void deleteByIds(ID... ids);

    /**
     * 根据ID查找实体信息
     * @param id 实体对象ID
     * @return
     */
    T findById(ID id);

    /**
     * 根据ID查找实体信息（包含已删除）
     * @param id 实体对象ID
     * @return
     */
    T findByIdAll(ID id);

    /**
     * 根据参数查询实体数量
     * @param params 查询参数
     * @return
     */
    long queryForCount(Map<String, Object> params);

    /**
     * 根据参数查询实体信息列表
     * @param params 查询参数
     * @return
     */
    List<T> queryForList(Map<String, Object> params);

    /**
     * 根据参数查询实体信息列表，支持排序功能
     * @param params 查询参数
     * @param sort 排序参数
     * @return
     */
    List<T> queryForList(Map<String, Object> params, Sort sort);

    /**
     * 根据参数信息和分页信息查询实体信息列表
     * @param params 查询参数
     * @param pageable 分页信息
     * @return
     */
    Page<T> queryForPage(Map<String, Object> params, Pageable pageable);

    /**
     * 根据条件查询返回单个对象
     * @param params 查询参数
     * @return
     */
    T queryForSingle(Map<String, Object> params);
}
