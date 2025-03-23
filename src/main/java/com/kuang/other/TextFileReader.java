package com.kuang.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: JunYu
 * @Date: 2024/6/5 20:36
 * @Description:
 * @Version: V1.0.0
 */
public class TextFileReader {

    public static void main(String[] args) throws URISyntaxException, IOException {
        //如何快速将文本文件读取为字符串
        URL resource = TextFileReader.class.getClassLoader().getResource("templates/test.json");
        URI uri;
        if (resource != null) {
            uri = resource.toURI();
            Path path = Paths.get(uri);
            byte[] bytes = Files.readAllBytes(path);
            String s = new String(bytes);
            System.out.println(s);
            JSONObject jsonObject = JSON.parseObject(s);
            System.out.println(jsonObject);
        }

        //如何将Map对象转换为json字符串并写入文本文件
        Map<String, Object> map = new HashMap<>();
        map.put("name","李四");
        map.put("age",30);
        map.put("gender",0);
        String s = JSON.toJSONString(map);
        Path path = Paths.get("a.txt");
//        Files.write(path,s.getBytes(StandardCharsets.UTF_8));

        String collect = Files.lines(Paths.get(resource.toURI())).collect(Collectors.joining("|"));
        System.out.println(collect);
    }
}
