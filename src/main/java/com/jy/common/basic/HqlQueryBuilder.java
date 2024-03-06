package com.jy.common.basic;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 19:28
 * @Description:
 * @Version: V1.0.0
 */

public class HqlQueryBuilder {

    private final String SQL_STRING = "SQLSTRING";

    private final Map<String, String> operatorMap = ImmutableMap.<String, String>builder()
            .put("LIKE", "like")
            .put("NOT_LIKE", "not like")
            .put("EQ", "=")
            .put("NOT_EQ", "<>")
            .put("IN", "in")
            .put("NOT_IN", "not in")
            .put("GT", ">")
            .put("GTE", ">=")
            .put("LT", "<")
            .put("LTE", "<=")
            .put("NULL", "is null")
            .put("NOT_NULL", "is not null").build();

    private EntityManager entityManager;

    public HqlQueryBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TypedQuery<Long> buildCountQuery(Class clazz, Map<String, Object> dslParamMap) {
        StringBuffer hql = new StringBuffer("select count(*) from " + clazz.getSimpleName() + " t1 where 1=1");
        List<Condition> conditions = getQueryConditions(dslParamMap);
        String whereFragment = buildHQLWhereFragment(conditions);
        TypedQuery<Long> query = entityManager.createQuery(hql.toString() + whereFragment, Long.class);
        conditions.forEach( (condition) -> {
            if(condition.placeholder != null){
                query.setParameter(condition.placeholder, condition.value);
            }
        } );
        return query;
    }

    public Query buildListQuery(Class clazz, Map<String, Object> dslParamMap, Pageable pageable) {
        StringBuffer hql = new StringBuffer("from " + clazz.getSimpleName() + " t1 where 1 = 1 ");
        List<Condition> conditions = getQueryConditions(dslParamMap);
        String whereFragment = buildHQLWhereFragment(conditions);
        String orderFragment = buildHQLOrderFragment(pageable);
        Query query = entityManager.createQuery(hql.toString() + whereFragment + orderFragment);
        conditions.forEach( (condition)-> {
            if(condition.placeholder != null){
                query.setParameter(condition.placeholder, condition.value);
            }
        } );
        if (pageable != null) {
            return query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        }
        return query;
    }

    private String buildHQLOrderFragment(Pageable pageable){
        if(pageable == null || pageable.getSort() == null){
            return "";
        }
        String buffer = "";
        StringBuilder hql = new StringBuilder(buffer);
        pageable.getSort().forEach( (order) -> {
            if(hql.toString().isEmpty()) {
                hql.append(order.getProperty() + " " + order.getDirection().name());
            }else{
                hql.append("," + order.getProperty() + " " + order.getDirection().name());
            }
        });
        if(hql.toString().isEmpty()){
            return "";
        }
        return " order by " + hql.toString();
    }

    private String buildHQLWhereFragment(List<Condition> conditions){
        int i = 0;
        StringBuilder hql = new StringBuilder();
        for(Condition condition: conditions){

            if(condition.value == null
                    && !StringUtils.equalsIgnoreCase(condition.operator, "NULL")
                    && !StringUtils.equalsIgnoreCase(condition.operator, "NOT_NULL")) {
                continue;
            }

            String placeholder = "parameter" + i++;
            switch(condition.operator){
                case "LIKE":
                case "NOT_LIKE":
                case "EQ":
                case "NOT_EQ":
                case "IN":
                case "NOT_IN":
                case "GT":
                case "GTE":
                case "LT":
                case "LTE":
                    if ("GT".equals(condition.operator) || "GTE".equals(condition.operator) || "LT".equals(condition.operator) || "LTE".equals(condition.operator)){
                        hql.append(" and " + condition.paramName + " " + operatorMap.get(condition.operator) + " TO_NUMBER(" + " :" + placeholder + ")");
                    }else {
                        hql.append(" and " + condition.paramName + " " + operatorMap.get(condition.operator) + " :" + placeholder);
                    }
                    condition.placeholder = placeholder;
                    break;
                case "NULL":
                case "NOT_NULL":
                    hql.append(" and " + condition.paramName + " " + operatorMap.get(condition.operator));
                    break;
                case SQL_STRING:
                    hql.append(" and " + condition.value);
                    break;
            }
        }
        return hql.toString();
    }

    private List<Condition> getQueryConditions(Map<String, Object> dslParamMap){
        List<Condition> conditions = new ArrayList<>(8);
        Pattern pattern = Pattern.compile("(.+)\\((.+)\\)", Pattern.CASE_INSENSITIVE);
        for(String key: dslParamMap.keySet()){
            //如果是sqlstring
            if(StringUtils.equalsIgnoreCase(SQL_STRING, key)){
                conditions.add(new Condition(SQL_STRING, SQL_STRING, dslParamMap.get(key)));
                continue;
            }
            Matcher matcher = pattern.matcher(key);
            if(matcher.groupCount() != 2 || !matcher.find()){
                continue;
            }
            conditions.add(new Condition(matcher.group(2), matcher.group(1).toUpperCase(), dslParamMap.get(key)));
        }
        return conditions;
    }

    static class Condition{
        /**
         *参数路径
         */
        private String paramName;
        /**
         * 操作符
         */
        private String operator;
        /**
         * 参数值
         */
        private Object value;
        /**
         * 参数占位符，默认值为空，只有在用到时才不为空
         */
        private String placeholder;

        public Condition(String paramName, String operator, Object value) {
            this.paramName = paramName;
            this.operator = operator;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Condition{" +
                    "paramName='" + paramName + '\'' +
                    ", operator='" + operator + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
