package com.jy.attack.controller;

import com.jy.attack.entity.Chapter;
import com.jy.attack.entity.Levels;
import com.jy.attack.service.ChapterService;
import com.jy.attack.service.LevelsService;
import com.jy.attack.vo.ChapterQuery;
import com.jy.attack.vo.EnemyNumVO;
import com.jy.common.utils.ExcelUtil;
import com.jy.common.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: JunYu
 * @Date: 2024/3/21 20:41
 * @Description:
 * @Version: V1.0.0
 */
@Controller
@RequestMapping("/wushuang")
public class WuShuangController {

    @Resource
    ChapterService chapterService;
    @Resource
    LevelsService levelsService;

    @GetMapping("/zj")
    public String toWsZjListUI() {
        return "zjgq/ws/wszjList";
    }

    @GetMapping("/zj/list")
    @ResponseBody
    public Result<Object> getWsZjList(ChapterQuery query) {
        List<Chapter> list = chapterService.listByQuery(query);
        Long count = chapterService.countByQuery(query);
        return Result.success(list, count);
    }

    @GetMapping("/gq")
    public String toWsGqListUI(Long chapterId, Model model) {
        model.addAttribute("chapterId", chapterId);
        return "zjgq/ws/wsgqList";
    }

    @GetMapping("/gq/list")
    @ResponseBody
    public Result<Object> getWsGqList(Long chapterId) {
        List<Levels> list = levelsService.listByChapterId(chapterId);
        Long count = levelsService.countByChapterId(chapterId);
        return Result.success(list, count);
    }

    @GetMapping("/jz")
    public String toWsJzListUI() {
        return "zjgq/ws/wsjzList";
    }

    @GetMapping("/jz/list")
    @ResponseBody
    public Result<Object> getWsJzList() {
        List<EnemyNumVO> list = chapterService.enemyNumByCategory("1");
        return Result.success(list, (long) list.size());
    }

    @GetMapping("/jz/export")
    public void export(HttpServletResponse response) {
        List<EnemyNumVO> list = chapterService.enemyNumByCategory("1");
        String[] headers = {"章节", "关卡1", "关卡2", "关卡3", "关卡4", "关卡5", "关卡6"};
        response.reset();
        response.setContentType("application/vnd..ms-excel");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("无双击坠.xlsx", "utf-8"));
            ExcelUtil.export(list, EnemyNumVO.class, response.getOutputStream(), headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
