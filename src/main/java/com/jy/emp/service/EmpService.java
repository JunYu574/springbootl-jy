package com.jy.emp.service;

import com.jy.emp.entity.Dept;
import com.jy.emp.entity.Emp;
import com.jy.emp.vo.EmpQuery;

import java.util.List;

public interface EmpService {

    /**
     * 根据条件查询员工列表
     * @param param
     * @return
     */
    List<Emp> getEmpList(EmpQuery param);

    /**
     * 根据条件查询员工数量
     * @param param
     * @return
     */
    Long countEmpList(EmpQuery param);

    /**
     * 新增员工
     * @param emp
     */
    void addEmp(Emp emp);

    /**
     * 查询部门集合
     * @return
     */
    List<Dept> getAllDept();

    /**
     * 根据Id批量删除员工
     * @param ids
     */
    void deleteEmpByIds(String ids);

    /**
     * 通过Id查询员工信息
     * @param id
     * @return
     */
    Emp getEmpById(Integer id);

    /**
     * 修改员工信息
     * @param emp
     */
    void updateEmp(Emp emp);
}
