package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.User;
import cn.salesManagementSystem.service.IUserService;
import cn.salesManagementSystem.utils.Constants;
import cn.salesManagementSystem.utils.JsonUtil;
import cn.salesManagementSystem.utils.ResJson;
import cn.salesManagementSystem.utils.UtilTools;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
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
    public String getUserList(@RequestParam Integer current, @RequestParam Integer size, HttpSession session) {
        // 鉴权，本接口只允许超级管理员和门店管理员使用
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        if (!UtilTools.checkNull(new Object[]{current, size})) {
            return ResJson.IS_NULL_RETURN_JSON;
        }
        String roleIdStr = session.getAttribute("roleId").toString();
        String storeIdStr = session.getAttribute("storeId").toString();
        IPage<User> page1 = this.userService.getUserList(new Page<>(current, size), Long.valueOf(roleIdStr.trim()), Long.valueOf(storeIdStr.trim()));
        List<User> userList = page1.getRecords();
        try {
            return JsonUtil.listToLayJson(FIELDS, userList, page1.getTotal());
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
            return ResJson.DATA_ALREADY_EXISTS_JSON;
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
        wrapper.select("id", "role_id", "store_id", "enable_status");
        wrapper.eq("username", username.trim());
        wrapper.eq("password", UtilTools.passwordEncryption(password, username));
        User user = this.userService.getOne(wrapper);
        if (user == null) {
            return ResJson.FAIL_RETURN_JSON;
        }
        if(user.getEnableStatus() == 0){
            return ResJson.USERN_NOT_ENABLE_JSON;
        }
        session.setAttribute("userId", user.getId().toString());
        session.setAttribute("roleId", user.getRoleId().toString());
        session.setAttribute("storeId", user.getStoreId().toString());
        return ResJson.SUCCESS_RETURN_JSON.substring(0,ResJson.SUCCESS_RETURN_JSON.length() - 1) + ",\"data\":{\"roleId\":\"" + user.getRoleId().toString() + "\"}}";
    }

    @ApiOperation("登出接口")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return ResJson.SUCCESS_RETURN_JSON;
    }

    @ApiOperation("登录检查接口")
    @GetMapping(value = "/checkLogin")
    public String checkLogin(@RequestParam Integer role, HttpSession session){
        if(UtilTools.checkLogin(session, role)){
            return ResJson.SUCCESS_RETURN_JSON;
        }else {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
    }

    @ApiOperation(value = "更新用户信息接口")
    @PostMapping(value = "/update")
    public String update(@RequestBody User user, HttpSession session){
        if(!UtilTools.checkLogin(session, Constants.ROLE_ALL)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        String roleIdStr = session.getAttribute("roleId").toString();
        String userIdStr = session.getAttribute("userId").toString();
        String storeIdStr = session.getAttribute("storeId").toString();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",user.getId());
        // 权限检查，超级管理员可更新所有人数据，门店管理员可更新本门店人的数据，销售员仅能更新自己的数据
        switch (roleIdStr){
            case Constants.ROLE_SUPER_ADMIN_STR:
                // 仅超级管理员可修改用户角色和用户所属门店
                if(UtilTools.checkNull(user.getRoleId())){
                    wrapper.set("role_id", user.getRoleId());
                }
                if(UtilTools.checkNull(user.getStoreId())){
                    wrapper.set("store_id", user.getStoreId());
                }
                // 超级管理员和门店管理员均可修改用户启用状态
                if(UtilTools.checkNull(user.getEnableStatus())){
                    wrapper.set("enable_status", user.getEnableStatus());
                }
                break;
            case Constants.ROLE_SHOP_ADMIN_STR:
                // 查询待修改人员是否为本门店人员
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", user.getId());
                queryWrapper.select("store_id");
                User selected = this.userService.getOne(queryWrapper);
                // 不为本门店人员则返回操作失败
                if(!selected.getStoreId().toString().equals(storeIdStr)){
                    return ResJson.FAIL_RETURN_JSON;
                }
                // 超级管理员和门店管理员均可修改用户启用状态
                if(UtilTools.checkNull(user.getEnableStatus())){
                    wrapper.set("enable_status", user.getEnableStatus());
                }
                break;
            case Constants.ROLE_SALESMAN_STR:
                // 判断待修改人员是否为自己，不为自己则返回操作失败
                if(!user.getId().toString().equals(userIdStr)){
                    return ResJson.FAIL_RETURN_JSON;
                }
                break;
            default:
                return ResJson.FAIL_RETURN_JSON;
        }
        // 如果前台传入相关数据则修改相关数据
        if(UtilTools.checkNull(user.getPassword())){
            wrapper.set("password", UtilTools.passwordEncryption(user.getPassword(), user.getUsername()));
        }
        if(UtilTools.checkNull(user.getRealName())){
            wrapper.set("real_name", user.getRealName().trim());
        }
        if(UtilTools.checkNull(user.getEmail())){
            wrapper.set("email", user.getEmail().trim());
        }
        log.info(user);
        // 执行更新操作，返回是否成功
        boolean flag = this.userService.update(wrapper);
        if(flag){
            return ResJson.SUCCESS_RETURN_JSON;
        }else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation(value = "单个删除用户接口")
    @PostMapping(value = "/deleteById")
    public String deleteById(@RequestParam Long id, HttpSession session){
        if(!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        String roleIdStr = session.getAttribute("roleId").toString();
        String storeIdStr = session.getAttribute("storeId").toString();
        wrapper.eq("id", id);
        if(roleIdStr.equals(Constants.ROLE_SHOP_ADMIN_STR)){
            // 查询待删除人员是否为本门店人员
            wrapper.select("store_id");
            User selected = this.userService.getOne(wrapper);
            // 不为本门店人员则返回操作失败
            if(!selected.getStoreId().toString().equals(storeIdStr)){
                return ResJson.FAIL_RETURN_JSON;
            }
        }
        boolean flag = this.userService.remove(wrapper);
        if(flag){
            return ResJson.SUCCESS_RETURN_JSON;
        }else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation(value = "批量删除用户接口")
    @PostMapping(value = "/multiDelete")
    public String multiDelete(@RequestParam Long[] ids, HttpSession session){
        if(!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        String roleIdStr = session.getAttribute("roleId").toString();
        String storeIdStr = session.getAttribute("storeId").toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        List<Long> idList =  Arrays.asList(ids);
        wrapper.in("id", idList);
        if(roleIdStr.equals(Constants.ROLE_SHOP_ADMIN_STR)){
            wrapper.eq("store_id", storeIdStr);
            int count = this.userService.count(wrapper);
            if(count != ids.length){
                return ResJson.FAIL_RETURN_JSON;
            }
        }
        boolean flag = this.userService.remove(wrapper);
        if(flag){
            return ResJson.SUCCESS_RETURN_JSON;
        }else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

}
