package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.Inventory;
import cn.salesManagementSystem.entity.User;
import cn.salesManagementSystem.service.IInventoryService;
import cn.salesManagementSystem.service.IUserService;
import cn.salesManagementSystem.utils.Constants;
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
import java.util.Arrays;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品库存Controller层实现
 * @date 2021-04-30 22:24
 **/

@RestController
@RequestMapping(value = "/inventory")
@Log4j
@Api(tags = "库存管理相关接口")
public class InventoryController {
    public static final String[] FIELDS = {"id", "store_id", "goods_id", "inventory", "create_time", "update_time"};
    @Resource
    IInventoryService inventoryService;
    @Resource
    IUserService userService;

    @GetMapping("/getInventoryList")
    @ApiOperation("获取库存列表接口")
    public String getInventoryList(HttpSession session, @RequestParam Integer current, @RequestParam Integer size) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_ALL)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        Integer storeId = null;
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            storeId = (Integer) session.getAttribute("storeId");
        }
        IPage<Inventory> page = this.inventoryService.getInventoryList(storeId, new Page<>(current, size));
        return JsonUtil.listToLayJson(FIELDS, page.getRecords(), page.getTotal());
    }

    @ApiOperation("添加或修改库存信息接口")
    @PostMapping("/addOrUpdateInventory")
    public String saveOrUpdateInventory(HttpSession session, @RequestBody Inventory inventory) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        User loginUser = this.userService.getById(session.getAttribute("userId").toString());
        if (inventory.getStoreId() != null && !loginUser.getStoreId().equals(inventory.getStoreId())) {
            return ResJson.FAIL_RETURN_JSON;
        }
        inventory.setStoreId(loginUser.getStoreId());
        boolean flag = this.inventoryService.saveOrUpdate(inventory);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("删除库存信息接口")
    @PostMapping("/deleteInventory")
    public String deleteInventory(HttpSession session, @RequestParam Long id) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        User loginUser = this.userService.getById(session.getAttribute("userId").toString());
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("store_id", loginUser.getStoreId());
        boolean flag = this.inventoryService.remove(queryWrapper);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("批量删除库存接口")
    @PostMapping("/multiDeleteInventory")
    public String multiDeleteInventory(HttpSession session, @RequestParam Long[] ids) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        User loginUser = this.userService.getById(session.getAttribute("userId").toString());
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", Arrays.asList(ids));
        queryWrapper.eq("store_id", loginUser.getStoreId());
        boolean flag = this.inventoryService.remove(queryWrapper);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }
}
