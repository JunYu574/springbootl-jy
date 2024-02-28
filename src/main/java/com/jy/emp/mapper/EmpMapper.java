package com.jy.emp.mapper;

import com.jy.emp.entity.Emp;
import com.jy.emp.vo.EmpQuery;

import java.util.List;

public interface EmpMapper {

    List<Emp> getEmpList(EmpQuery param);

    Long countEmpList(EmpQuery param);

    void addEmp(Emp emp);
}
