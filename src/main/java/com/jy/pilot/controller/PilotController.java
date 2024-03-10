package com.jy.pilot.controller;

import com.jy.common.vo.Result;
import com.jy.pilot.entity.Pilot;
import com.jy.pilot.service.PilotService;
import com.jy.pilot.vo.PilotQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/5 13:10
 * @Description:
 * @Version: V1.0.0
 */
@Controller
@RequestMapping("/pilot")
public class PilotController {

    @Autowired
    private PilotService pilotService;

    @GetMapping("")
    public String toPilotListUI(){
        return "pilot/pilotList";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Object> getPilotList(PilotQuery query){
        List<Pilot> list = pilotService.pageByQuery(query);
        Long count = pilotService.countByQuery(query);
        return Result.success(list,count);
    }

    @PostMapping("")
    @ResponseBody
    public Result<Object> addPilot(Pilot pilot){
        pilotService.save(pilot);
        return Result.success("新增机师成功！");
    }

    @GetMapping("/add/ui")
    public String toAddUI(){
        return "pilot/pilotAdd";
    }

    @DeleteMapping("/{ids}")
    @ResponseBody
    public Result<Object> deleteEmpByIds(@PathVariable("ids") Long... ids){
        pilotService.deleteByIds(ids);
        return Result.success("删除员工成功");
    }

    @GetMapping("/{id}")
    public String getEmpById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pilot", pilotService.findById(id));
        return "pilot/pilotEdit";
    }

    @PutMapping("")
    @ResponseBody
    public Result<Object> updateEmp(Pilot pilot){
        pilotService.update(pilot);
        return Result.success("机师信息修改成功");
    }
}
