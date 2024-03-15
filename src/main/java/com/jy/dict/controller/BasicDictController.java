package com.jy.dict.controller;

import com.jy.common.cache.CacheDict;
import com.jy.common.constants.DictionaryConstants;
import com.jy.common.utils.DictUtils;
import com.jy.common.vo.Result;
import com.jy.dict.entity.GlobalDictionary;
import com.jy.dict.entity.GlobalDictionarySub;
import com.jy.dict.service.GlobalDictionaryService;
import com.jy.dict.service.GlobalDictionarySubService;
import com.jy.dict.vo.DictQuery;
import com.jy.dict.vo.SubDictQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: JunYu
 * @Date: 2024/3/7 12:37
 * @Description:
 * @Version: V1.0.0
 */
@Controller
@RequestMapping(value = "/conf/dict")
public class BasicDictController {

    @Resource
    DictUtils dictUtils;
    @Resource
    GlobalDictionaryService globalDictionaryService;
    @Resource
    GlobalDictionarySubService globalDictionarySubService;

    @GetMapping("")
    public String toDictListUI(Model model){
        //字典分类
        model.addAttribute("dictCatalog", CacheDict.dictMap.get(DictionaryConstants.CACHE_DICT_CATALOG));
        return "dict/dictList";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Object> getDictList(DictQuery query){
        List<GlobalDictionary> list = globalDictionaryService.pageByQuery(query);
        Long count = globalDictionaryService.countByQuery(query);
        //字典分类
        Map<String, String> dictTypeMap = CacheDict.dictMap.get(DictionaryConstants.CACHE_DICT_CATALOG);
        list.stream().filter(dict -> StringUtils.isNotBlank(dict.getDictType()))
                .forEach(dict -> dict.setDictTypeName(dictTypeMap.containsKey(dict.getDictType()) ? dictTypeMap.get(dict.getDictType()) : dict.getDictType()));
        return Result.success(list,count);
    }

    @PostMapping("")
    @ResponseBody
    public Result<Object> addDict(GlobalDictionary dict){
        globalDictionaryService.save(dict);
        return Result.success("新增字典成功！");
    }

    @GetMapping("/add/ui")
    public String toAddUI(Model model){
        //字典分类
        model.addAttribute("dictCatalog", CacheDict.dictMap.get(DictionaryConstants.CACHE_DICT_CATALOG));
        return "dict/dictAdd";
    }

    @DeleteMapping("/{ids}")
    @ResponseBody
    public Result<Object> deleteDictByIds(@PathVariable("ids") Long... ids){
        for (Long id : ids){
            GlobalDictionary dict = globalDictionaryService.findById(id);
            dict.getSubDicts().forEach(sunDict -> globalDictionarySubService.deleteById(sunDict.getId()));
            globalDictionaryService.deleteById(id);
        }
        return Result.success("删除字典成功");
    }

    @GetMapping("/{id}")
    public String getDictById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("dict", globalDictionaryService.findById(id));
        model.addAttribute("dictCatalog", CacheDict.dictMap.get(DictionaryConstants.CACHE_DICT_CATALOG));
        return "dict/dictEdit";
    }

    @PutMapping("")
    @ResponseBody
    public Result<Object> updateDict(GlobalDictionary dict){
        globalDictionaryService.update(dict);
        return Result.success("字典信息修改成功");
    }


    @GetMapping("/sub/dict/{dictId}")
    public String toSubDictListUI(@PathVariable("dictId") Long dictId, Model model){
        model.addAttribute("dictId", dictId);
        return "dict/subDictList";
    }

    @GetMapping("/sub/list")
    @ResponseBody
    public Result<Object> getSubDictList(SubDictQuery query){
        List<GlobalDictionarySub> list = globalDictionarySubService.pageByQuery(query);
        Long count = globalDictionarySubService.countByQuery(query);
        return Result.success(list,count);
    }

    @PostMapping("/sub/{dictId}")
    @ResponseBody
    public Result<Object> addSubDict(@PathVariable("dictId") Long dictId, GlobalDictionarySub subDict){
        GlobalDictionary dict = globalDictionaryService.findById(dictId);
        subDict.setDict(dict);
        globalDictionarySubService.save(subDict);
        return Result.success("新增字典子项成功！");
    }

    @GetMapping("/sub/add/ui/{dictId}")
    public String toSubAddUI(@PathVariable("dictId") Long dictId, Model model){
        model.addAttribute("dictId", dictId);
        return "dict/subDictAdd";
    }

    @DeleteMapping("/sub/{ids}")
    @ResponseBody
    public Result<Object> deleteSubDictByIds(@PathVariable("ids") Long... ids){
        globalDictionarySubService.deleteByIds(ids);
        return Result.success("删除字典子项成功");
    }

    @GetMapping("/sub/{id}")
    public String getSubDictById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("subDict", globalDictionarySubService.findById(id));
        return "dict/subDictEdit";
    }

    @PutMapping("/sub")
    @ResponseBody
    public Result<Object> updateSubDict(GlobalDictionarySub subDict){
        globalDictionarySubService.update(subDict);
        return Result.success("字典信息修改成功");
    }

    @PostMapping("/refresh/cache")
    @ResponseBody
    public Result<Object> refreshCacheDict(){
        dictUtils.refreshCacheDictionary();
        return Result.success("字典缓存刷新成功");
    }

    @PutMapping("/sub/enabled/{id}")
    @ResponseBody
    public Result<Object> subDictEnabled(@PathVariable("id") Long id){
        GlobalDictionarySub subDict = globalDictionarySubService.findById(id);
        subDict.setEnabled(!subDict.isEnabled());
        globalDictionarySubService.update(subDict);
        return Result.success("字典子项启用状态已修改");
    }

}
