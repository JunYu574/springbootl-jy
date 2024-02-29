package com.jy.emp.service;

import com.jy.emp.entity.Dept;
import com.jy.emp.entity.Emp;
import com.jy.emp.vo.EmpQuery;

import java.util.List;

public interface EmpService {

    List<Emp> getEmpList(EmpQuery param);

    Long countEmpList(EmpQuery param);

    void addEmp(Emp emp);

    List<Dept> getAllDept();

    void deleteEmpByIds(String ids);
}
