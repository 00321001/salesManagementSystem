package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.utils.Constants;
import cn.salesManagementSystem.utils.UtilTools;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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
    @GetMapping(value = "/login")
    public String index() {
        return "login";
    }

    @GetMapping(value = "/signUp")
    public String signUp() {
        return "signUp";
    }

    @GetMapping(value = "/systemAdmin")
    public String systemAdmin(HttpSession session){
        if(UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)){
            return "systemAdmin";
        }else {
            return "login";
        }
    }
    @GetMapping(value = "userManage")
    public String userManage(HttpSession session){
        if(UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)){
            return "userManage";
        }else {
            return "login";
        }
    }
}
