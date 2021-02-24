package cn.salesManagementSystem.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 页面相关接口Controller层实现
 * @date 2021-02-24 15:12
 **/
@Api(value = "页面相关接口")
@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping(value = "/signIn")
    public String index() {
        return "signIn";
    }

    @GetMapping(value = "/signUp")
    public String signup() {
        return "signUp";
    }
}
