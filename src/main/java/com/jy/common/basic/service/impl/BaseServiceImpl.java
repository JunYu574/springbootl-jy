package com.jy.common.basic.service.impl;

import com.google.common.collect.Maps;
import com.jy.common.basic.dao.BaseRepository;
import com.jy.common.basic.entiry.BaseEntity;
import com.jy.common.basic.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: JunYu
 * @Date: 2024/3/4 11:39
 * @Description:
 * @Version: V1.0.0
 */
@CacheConfig(cacheNames = "defaultCache")
public abstract class BaseServiceImpl<T extends BaseEntity, ID> implements BaseService<T, ID> {

    @Autowired
    protected BaseRepository<T, ID> repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public T update(T entity) {
        entity.setUpdateTime(new Date());
        return repository.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteItem(T entity) {
        repository.delete(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteItems(List<T> entities) {
        repository.deleteAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(ID id) {
        Optional<T> result = repository.findById(id);
        if(result.isPresent()){
            result.get().setUpdateTime(new Date());
            result.get().setDeleted(true);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(ID... ids) {
        for(ID id: ids){
            deleteById(id);
        }
    }

    @Override
    public T findById(ID id) {
        Optional<T> result = repository.findById(id);
        if(result.isPresent() && !result.get().isDeleted()){
            return result.get();
        }
        return null;
    }

    @Override
    public T findByIdAll(ID id) {
        Optional<T> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    public long queryForCount(Map<String, Object> params) {
        return repository.queryForCount(params);
    }

    @Override
    public List<T> queryForList(Map<String, Object> params) {
        return repository.queryForList(params);
    }

    @Override
    public List<T> queryForList(Map<String, Object> params, Sort sort) {
        return repository.queryForList(params, sort);
    }

    @Override
    public List<T> queryForList(Map<String, Object> params, Pageable pageable) {
        return repository.queryForList(params, pageable);
    }

    @Override
    public T queryForSingle(Map<String, Object> params) {
        return repository.queryForSingle(addDefaultConditions(params));
    }

    /**
     * 添加默认删除条件
     * @param params
     * @return
     */
    protected Map<String, Object> addDefaultConditions(Map<String, Object> params){
        Map<String, Object> newParams = Maps.newLinkedHashMap();
        newParams.putAll(params);
        if(!params.keySet().stream().anyMatch(key ->
                StringUtils.equalsIgnoreCase(key, "eq(deleted)")) && BaseEntity.class.isAssignableFrom(repository.getDomainClass())){
            newParams.put("EQ(deleted)", Boolean.FALSE);
        }
        newParams.put("sort(sort)","asc");
        return newParams;
    }


    protected Pageable getPage(Integer pageNumber, Integer pageSize, Sort sort){
        return PageRequest.of((pageNumber == 0 ? 1 : pageNumber) - 1, pageSize, sort != null ? sort : Sort.by(Sort.Direction.ASC, "createTime"));
    }
}
