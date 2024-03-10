package com.jy.common.basic.dao.impl;

import com.jy.common.basic.DbType;
import com.jy.common.basic.HqlQueryBuilder;
import com.jy.common.basic.dao.BaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 19:14
 * @Description:
 * @Version: V1.0.0
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    protected final EntityManager entityManager;

    protected HqlQueryBuilder hqlQueryBuilder;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        hqlQueryBuilder = new HqlQueryBuilder(entityManager);
    }

    @Override
    public int getDbType() {
        Map<String, Object> properties = entityManager.getEntityManagerFactory().getProperties();
        String hibernateDialect = (String)properties.get("hibernate.dialect");
        if(StringUtils.containsIgnoreCase(hibernateDialect, "mysql")){
            return DbType.MYSQL;
        }
        if(StringUtils.containsIgnoreCase(hibernateDialect, "oracle")){
            return DbType.ORACLE;
        }
        return DbType.DEFAULT;
    }

    @Override
    public Class<T> getDomainClass() {
        return super.getDomainClass();
    }

    @Override
    public long queryForCount(Map<String, Object> dslParamMap) {
       return hqlQueryBuilder.buildCountQuery(getDomainClass(), dslParamMap).getSingleResult();
    }

    @Override
    public List<T> queryForList(Map<String, Object> dslParamMap) {
        return hqlQueryBuilder.buildListQuery(getDomainClass(), dslParamMap, null).getResultList();
    }

    @Override
    public List<T> queryForList(Map<String, Object> dslParamMap, Sort sort) {
        return hqlQueryBuilder.buildListQuery(getDomainClass(), dslParamMap, PageRequest.of(0, Integer.MAX_VALUE, sort)).getResultList();
    }

    @Override
    public List<T> queryForList(Map<String, Object> dslParamMap, Pageable pageable) {
        return hqlQueryBuilder.buildListQuery(getDomainClass(), dslParamMap, pageable).getResultList();
    }

    @Override
    public T queryForSingle(Map<String, Object> dslParamMap) {
        List<T> content = queryForList(dslParamMap);
        return content.isEmpty() ? null : content.get(0);
    }

    @Override
    public Page<T> queryForPage(String sql, Pageable pageable) {
        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        String prefix = "order by ";
        String orderBy = prefix;
        while(iterator.hasNext()){
            Sort.Order order = iterator.next();
            if(orderBy.equals(prefix)) {
                orderBy += order.getProperty() + " " + order.getDirection().name();
            }else{
                orderBy += "," + order.getProperty() + " " + order.getDirection().name();
            }
        }
        String countSql = "select count(*) from (" + sql + ") k";
        String selectSql = sql + orderBy;
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countSql).getSingleResult();
        List<T> content =  entityManager.createNativeQuery(selectSql, getDomainClass())
                .setFirstResult(pageable.getPageNumber()*pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<T>(content, pageable, count.longValue());
    }
}
