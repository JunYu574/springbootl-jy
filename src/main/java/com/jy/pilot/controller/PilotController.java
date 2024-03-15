package com.jy.pilot.controller;

import com.jy.common.cache.CacheDict;
import com.jy.common.constants.DictionaryConstants;
import com.jy.common.vo.Result;
import com.jy.pilot.entity.Pilot;
import com.jy.pilot.service.PilotService;
import com.jy.pilot.vo.PilotQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public String toPilotListUI(Model model){
        //机师势力
        model.addAttribute("pilotInfluence", CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_INFLUENCE_TYPE));
        //机师性格
        model.addAttribute("pilotDisposition", CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_DISPOSITION_TYPE));
        return "pilot/pilotList";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Object> getPilotList(PilotQuery query){
        List<Pilot> list = pilotService.pageByQuery(query);
        Long count = pilotService.countByQuery(query);
        //机师势力
        Map<String, String> pilotInfluenceMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_INFLUENCE_TYPE);
        list.stream().filter(pilot -> StringUtils.isNotBlank(pilot.getInfluence())).forEach(pilot -> pilot.setInfluenceName(pilotInfluenceMap.get(pilot.getInfluence())));
        //机师性格
        Map<String, String> pilotDispositionMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_DISPOSITION_TYPE);
        list.stream().filter(pilot -> StringUtils.isNotBlank(pilot.getDisposition())).forEach(pilot -> pilot.setDispositionName(pilotDispositionMap.get(pilot.getDisposition())));
        return Result.success(list,count);
    }

    @PostMapping("")
    @ResponseBody
    public Result<Object> addPilot(Pilot pilot){
        pilotService.save(pilot);
        return Result.success("新增机师成功！");
    }

    @GetMapping("/add/ui")
    public String toAddUI(Model model){
        //机师势力
        model.addAttribute("pilotInfluence", CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_INFLUENCE_TYPE));
        //机师性格
        model.addAttribute("pilotDisposition", CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_DISPOSITION_TYPE));
        return "pilot/pilotAdd";
    }

    @DeleteMapping("/{ids}")
    @ResponseBody
    public Result<Object> deletePilotByIds(@PathVariable("ids") Long... ids){
        pilotService.deleteByIds(ids);
        return Result.success("删除员工成功");
    }

    @GetMapping("/{id}")
    public String getPilotById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pilot", pilotService.findById(id));
        //机师势力
        model.addAttribute("pilotInfluence", CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_INFLUENCE_TYPE));
        //机师性格
        model.addAttribute("pilotDisposition", CacheDict.dictMap.get(DictionaryConstants.CACHE_PILOT_DISPOSITION_TYPE));
        return "pilot/pilotEdit";
    }

    @PutMapping("")
    @ResponseBody
    public Result<Object> updatePilot(Pilot pilot){
        pilotService.update(pilot);
        return Result.success("机师信息修改成功");
    }

    @PutMapping("/awakening/{id}")
    @ResponseBody
    public Result<Object> subDictEnabled(@PathVariable("id") Long id){
        Pilot pilot = pilotService.findById(id);
        pilot.setAwakening(!pilot.isAwakening());
        pilotService.update(pilot);
        return Result.success("机师觉醒状态已修改");
    }
}
