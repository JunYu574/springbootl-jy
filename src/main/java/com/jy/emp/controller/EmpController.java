package com.jy.emp.controller;

import com.jy.common.vo.Result;
import com.jy.emp.entity.Dept;
import com.jy.emp.entity.Emp;
import com.jy.emp.service.EmpService;
import com.jy.emp.vo.EmpQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/2/26 19:16
 * @Description:
 * @Version: V1.0.0
 */
@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("")
    public String toEmpListUI(){
        return "emp/empList";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Object> getEmpList(EmpQuery param){
        List<Emp> list = empService.getEmpList(param);
        Long count = empService.countEmpList(param);
        return Result.success(list,count);
    }

    @PostMapping("")
    @ResponseBody
    public Result<Object> addEmp(Emp emp){
        empService.addEmp(emp);
        return Result.success("新增员工成功！");
    }

    @GetMapping("/add/ui")
    public String toAddUI(Model model){
        List<Dept> deptList = empService.getAllDept();
        model.addAttribute("deptList", deptList);
        return "emp/empAdd";
    }

    @DeleteMapping("/{ids}")
    @ResponseBody
    public Result<Object> deleteEmpByIds(@PathVariable("ids") String ids){
        empService.deleteEmpByIds(ids);
        return Result.success("删除员工成功");
    }

}
