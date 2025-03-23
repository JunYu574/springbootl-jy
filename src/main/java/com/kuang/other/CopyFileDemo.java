package com.kuang.other;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author: JunYu
 * @Date: 2024/6/14 11:51
 * @Description: apache commons-io的相关工具方 法
 * @Version: V1.0.0
 */
public class CopyFileDemo {
    public static void main(String[] args) throws IOException {
        //1. JDK自带的文件复制、移动、删除
//        Files.copy(Paths.get("doc/1.md"),Paths.get("doc/2.md"));
//        Files.copy(Files.newInputStream(Paths.get("doc/1.md")), Paths.get("doc/3.md"), StandardCopyOption.REPLACE_EXISTING);
//        Files.copy(Paths.get("doc/1.md"),Files.newOutputStream(Paths.get("doc/4.md")));

//        Files.move(Paths.get("doc/4.md"),Paths.get("doc/5.md"));
//        Files.delete(Paths.get("doc/1.md"));

        // 2. apache commons-io的相关工具方法
//        FileUtils.copyDirectory(new File("doc"), new File("doc2"));
//        FileUtils.copyDirectoryToDirectory(new File("doc"), new File("doc2"));
//        FileUtils.copyFileToDirectory(new File("doc/2.md"), new File("doc2"));

        FileUtils.cleanDirectory(new File("doc2"));

    }
}
