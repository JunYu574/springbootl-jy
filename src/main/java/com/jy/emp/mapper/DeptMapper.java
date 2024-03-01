package com.jy.emp.mapper;

import com.jy.emp.entity.Dept;

import java.util.List;

public interface DeptMapper {

    /**
     * 查询部门集合
     * @return
     */
    List<Dept> getAllDept();

}
