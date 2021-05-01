package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.utils.Constants;
import cn.salesManagementSystem.utils.UtilTools;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 页面相关接口Controller层实现
 * @date 2021-02-24 15:12
 **/
@Api(value = "页面相关接口")
@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String index(HttpSession session) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_ALL)) {
            return "login";
        }
        switch (session.getAttribute("roleId").toString()) {
            case Constants.ROLE_SUPER_ADMIN_STR:
                return "systemAdmin";
            case Constants.ROLE_SHOP_ADMIN_STR:
                return "shopAdmin";
            case Constants.ROLE_SALESMAN_STR:
                return "salesman";
            default:
                return "login";
        }
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/signUp")
    public String signUp() {
        return "signUp";
    }

    @GetMapping(value = "/userManage")
    public String userManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return "userManage";
        } else {
            return "login";
        }
    }

    @GetMapping(value = "/storeManage")
    public String storeManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return "storeManage";
        } else {
            return "login";
        }
    }

    @GetMapping(value = "/goodsManage")
    public String goodsManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return "goodsManage";
        } else {
            return "login";
        }
    }

    @GetMapping("/goodsClassificationManage")
    public String goodsClassificationManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return "goodsClassificationManage";
        } else {
            return "login";
        }
    }

    @GetMapping("/inventoryManage")
    public String inventoryManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_ALL)) {
            return "inventoryManage";
        } else {
            return "login";
        }
    }

    @GetMapping("/recordManage")
    public String recordManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_ALL)) {
            return "recordManage";
        } else {
            return "login";
        }
    }

    @GetMapping("/serviceManage")
    public String serviceManage(HttpSession session) {
        if (UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return "serviceManage";
        } else {
            return "login";
        }
    }
}
