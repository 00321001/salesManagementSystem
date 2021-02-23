package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.User;
import cn.salesManagementSystem.service.IUserService;
import cn.salesManagementSystem.utils.JsonUtil;
import cn.salesManagementSystem.utils.UtilTools;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户管理相关接口Controller层实现
 * @create 2021-02-24 01:00
 **/

@RestController
@RequestMapping(value = "/user")
@Log4j
@Api(tags = "用户管理相关接口")
public class UserController {

    @Resource
    private IUserService userService;
    private static final String[] FIELDS = {"id", "username", "password", "roleId", "storeId", "realName", "email", "enableStatus", "createTime", "updateTime", "role", "store"};

    @GetMapping
    public String getUserList(@RequestParam Integer page, @RequestParam Integer limit, HttpSession session){
        session.setAttribute("roleId", "2");
        session.setAttribute("storeId", "2");
        String roleIdStr = session.getAttribute("roleId").toString();
        String storeIdStr = session.getAttribute("storeId").toString();
        IPage<User> page1 = new Page<>(page, limit);
        List<User> userList = this.userService.getUserList(page1, Long.valueOf(roleIdStr.trim()), Long.valueOf(storeIdStr.trim()));
        try{
            return JsonUtil.listToLayJson(FIELDS,userList);
        }catch (Exception e){
            log.error(e);
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
