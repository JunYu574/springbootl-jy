package com.jy.emp.service.impl;

import com.jy.emp.entity.Dept;
import com.jy.emp.entity.Emp;
import com.jy.emp.mapper.DeptMapper;
import com.jy.emp.mapper.EmpMapper;
import com.jy.emp.service.EmpService;
import com.jy.emp.vo.EmpQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/2/26 19:25
 * @Description:
 * @Version: V1.0.0
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Resource
    private EmpMapper empMapper;
    @Resource
    private DeptMapper deptMapper;

    @Override
    public List<Emp> getEmpList(EmpQuery param) {
        return empMapper.getEmpList(param);
    }

    @Override
    public Long countEmpList(EmpQuery param) {
        return empMapper.countEmpList(param);
    }

    @Override
    public void addEmp(Emp emp) {
        empMapper.addEmp(emp);
    }

    @Override
    public List<Dept> getAllDept() {
        return deptMapper.getAllDept();
    }

    @Override
    public void deleteEmpByIds(String ids) {
        empMapper.deleteEmpByIds(ids);
    }
}
