package com.jy.mecha.controller;

import com.jy.common.cache.CacheDict;
import com.jy.common.constants.DictionaryConstants;
import com.jy.common.vo.Result;
import com.jy.mecha.entity.Mecha;
import com.jy.mecha.service.MechaService;
import com.jy.mecha.vo.MechaQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/15 11:05
 * @Description:
 * @Version: V1.0.0
 */
@Controller
@RequestMapping("/mecha")
public class MechaController {

    @Autowired
    private MechaService mechaService;

    @GetMapping("")
    public String toMechaListUI(){
        return "mecha/mechaList";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Object> getMechaList(MechaQuery query){
        List<Mecha> list = mechaService.pageByQuery(query);
        Long count = mechaService.countByQuery(query);
        //机体势力
        Map<String, String> mechaInfluenceMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_INFLUENCE_TYPE);
        list.stream().filter(mecha -> StringUtils.isNotBlank(mecha.getInfluence())).forEach(mecha -> mecha.setInfluenceName(mechaInfluenceMap.get(mecha.getInfluence())));
        //机体形态
        Map<String, String> mechaShapeMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_SHAPE_TYPE);
        list.stream().filter(mecha -> StringUtils.isNotBlank(mecha.getShape())).forEach(mecha -> mecha.setShapeName(mechaShapeMap.get(mecha.getShape())));
        //机体稀有度
        Map<String, String> mechaRarityMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_RARITY_TYPE);
        list.stream().filter(mecha -> StringUtils.isNotBlank(mecha.getRarity())).forEach(mecha -> mecha.setRarityName(mechaRarityMap.get(mecha.getRarity())));
        //机体品质
        Map<String, String> mechaQualityMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_QUALITY_TYPE);
        list.stream().filter(mecha -> StringUtils.isNotBlank(mecha.getQuality())).forEach(mecha -> mecha.setQualityName(mechaQualityMap.get(mecha.getQuality())));
        //机体射程
        Map<String, String> mechaCombatMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_COMBAT_TYPE);
        list.stream().filter(mecha -> StringUtils.isNotBlank(mecha.getCombat())).forEach(mecha -> mecha.setCombatName(mechaCombatMap.get(mecha.getCombat())));
        return Result.success(list,count);
    }

    @PostMapping("")
    @ResponseBody
    public Result<Object> addMecha(Mecha mecha){
        mechaService.save(mecha);
        return Result.success("新增机体成功！");
    }

    @GetMapping("/add/ui")
    public String toAddUI(){
        return "mecha/mechaAdd";
    }

    @DeleteMapping("/{ids}")
    @ResponseBody
    public Result<Object> deleteMechaByIds(@PathVariable("ids") Long... ids){
        mechaService.deleteByIds(ids);
        return Result.success("删除机体成功");
    }

    @GetMapping("/{id}")
    public String getMechaById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("mecha", mechaService.findById(id));
        return "mecha/mechaEdit";
    }

    @PutMapping("")
    @ResponseBody
    public Result<Object> updateMecha(Mecha mecha){
        mechaService.update(mecha);
        return Result.success("机体信息修改成功");
    }

    @ModelAttribute
    public void addDictMap(Model model){
        //机体势力
        model.addAttribute("mechaInfluence", CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_INFLUENCE_TYPE));
        //机体形态
        model.addAttribute("mechaShape", CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_SHAPE_TYPE));
        //机体稀有度
        model.addAttribute("mechaRarity", CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_RARITY_TYPE));
        //机体品质
        model.addAttribute("mechaQuality", CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_QUALITY_TYPE));
        //机体射程
        model.addAttribute("mechaCombat", CacheDict.dictMap.get(DictionaryConstants.CACHE_MECHA_COMBAT_TYPE));
    }
}
