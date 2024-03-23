package com.jy.attack.controller;

import com.jy.attack.entity.Chapter;
import com.jy.attack.entity.Levels;
import com.jy.attack.service.ChapterService;
import com.jy.attack.service.LevelsService;
import com.jy.common.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: JunYu
 * @Date: 2024/3/22 19:07
 * @Description:
 * @Version: V1.0.0
 */
@Controller
@RequestMapping("/zjgq")
public class ChapterLevelsController {

    @Resource
    ChapterService chapterService;
    @Resource
    LevelsService levelsService;

    @PostMapping("/zj")
    @ResponseBody
    public Result<Object> addChapter(Chapter chapter){
        chapterService.save(chapter);
        return Result.success("新增章节成功！");
    }

    @GetMapping("/zj/add/ui/{category}")
    public String toAddChapterUI(@PathVariable("category") String category,Model model){
        model.addAttribute("category", category);
        return "zjgq/zjAdd";
    }

    @DeleteMapping("/zj/{ids}")
    @ResponseBody
    public Result<Object> deleteChapterByIds(@PathVariable("ids") Long... ids){
        for (Long id : ids){
            levelsService.deleteByChapterId(id);
            chapterService.deleteById(id);
        }
        return Result.success("删除章节成功");
    }

    @GetMapping("/zj/{id}")
    public String getChapterById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("chapter", chapterService.findById(id));
        return "zjgq/zjEdit";
    }

    @PutMapping("/zj")
    @ResponseBody
    public Result<Object> updateChapter(Chapter chapter){
        chapterService.update(chapter);
        return Result.success("章节信息修改成功");
    }

    @PostMapping("/gq")
    @ResponseBody
    public Result<Object> addLevels(Levels levels){
        levelsService.save(levels);
        return Result.success("新增关卡成功！");
    }

    @GetMapping("/gq/add/ui")
    public String toAddLevelsUI(String chapterId,Model model){
        model.addAttribute("chapterId", chapterId);
        return "zjgq/gqAdd";
    }

    @DeleteMapping("/gq/{ids}")
    @ResponseBody
    public Result<Object> deleteLevelsByIds(@PathVariable("ids") Long... ids){
        for (Long id : ids){
            levelsService.deleteById(id);
        }
        return Result.success("删除关卡成功");
    }

    @GetMapping("/gq/{id}")
    public String getLevelsById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("levels", levelsService.findById(id));
        return "zjgq/gqEdit";
    }

    @PutMapping("/gq")
    @ResponseBody
    public Result<Object> updateLevels(Levels levels){
        levelsService.update(levels);
        return Result.success("关卡信息修改成功");
    }

}
