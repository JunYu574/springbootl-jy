package com.jy.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: JunYu
 * @Date: 2024/3/1 19:16
 * @Description:
 * @Version: V1.0.0
 */
@Controller
public class NotFoundController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String error404(){
        return "error/404";
    }
}
