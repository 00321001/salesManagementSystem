package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.User;
import cn.salesManagementSystem.service.IUserService;
import cn.salesManagementSystem.utils.JsonUtil;
import cn.salesManagementSystem.utils.ResJson;
import cn.salesManagementSystem.utils.UtilTools;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户管理相关接口Controller层实现
 * @date 2021-02-24 01:00
 **/

@RestController
@RequestMapping(value = "/user")
@Log4j
@Api(tags = "用户管理相关接口")
public class UserController {

    private static final String[] FIELDS = {"id", "username", "roleId", "storeId", "realName", "email", "enableStatus", "createTime", "updateTime", "role", "store"};
    @Resource
    private IUserService userService;

    @ApiOperation(value = "获取用户信息列表接口")
    @GetMapping(value = "/getUserList")
    public String getUserList(@RequestParam Integer page, @RequestParam Integer limit, HttpSession session) {
        if (!UtilTools.checkLogin(session, 3)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        if (!UtilTools.checkNull(new Object[]{page, limit})) {
            return ResJson.IS_NULL_RETURN_JSON;
        }
        String roleIdStr = session.getAttribute("roleId").toString();
        String storeIdStr = session.getAttribute("storeId").toString();
        IPage<User> page1 = new Page<>(page, limit);
        List<User> userList = this.userService.getUserList(page1, Long.valueOf(roleIdStr.trim()), Long.valueOf(storeIdStr.trim()));
        try {
            return JsonUtil.listToLayJson(FIELDS, userList);
        } catch (Exception e) {
            log.error(e);
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("添加用户（注册）接口")
    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody User user) {
        if (!UtilTools.checkNull(new Object[]{user.getUsername(), user.getPassword(), user.getStoreId(), user.getRealName(), user.getEmail()})) {
            return ResJson.IS_NULL_RETURN_JSON;
        }
        user.setPassword(UtilTools.passwordEncryption(user.getPassword(), user.getUsername()));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", user.getUsername().trim());
        int count = this.userService.count(queryWrapper);
        if (count != 0) {
            return ResJson.DATA_ALREADY_EXISTS;
        } else {
            boolean flag = this.userService.addUser(user);
            if (flag) {
                return ResJson.SUCCESS_RETURN_JSON;
            } else {
                return ResJson.FAIL_RETURN_JSON;
            }
        }
    }

    @ApiOperation("登录接口")
    @GetMapping(value = "/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_id", "store_id");
        wrapper.eq("username", username.trim());
        wrapper.eq("password", UtilTools.passwordEncryption(password, username));
        User user = this.userService.getOne(wrapper);
        if (user == null) {
            return ResJson.FAIL_RETURN_JSON;
        }
        session.setAttribute("userId", user.getId().toString());
        session.setAttribute("roleId", user.getRoleId().toString());
        session.setAttribute("storeId", user.getStoreId().toString());
        return ResJson.SUCCESS_RETURN_JSON;
    }

    @ApiOperation("登出接口")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return ResJson.SUCCESS_RETURN_JSON;
    }

}
