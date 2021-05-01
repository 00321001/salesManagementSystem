package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.Inventory;
import cn.salesManagementSystem.entity.Record;
import cn.salesManagementSystem.entity.User;
import cn.salesManagementSystem.service.IInventoryService;
import cn.salesManagementSystem.service.IRecordService;
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

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 销售记录Controller层实现
 * @date 2021-05-01 10:30
 **/

@Api(tags = "销售记录相关接口")
@Log4j
@RequestMapping("/record")
@RestController
public class RecordController {

    public static final String[] FIELDS = {"id", "storeId", "goodsId", "userId", "recordStatus", "createTime", "realName", "storeName", "goodsName"};
    @Resource
    IRecordService recordService;
    @Resource
    IUserService userService;
    @Resource
    IInventoryService inventoryService;

    @GetMapping("/getRecordList")
    @ApiOperation("获取销售记录列表接口")
    public String getRecordList(HttpSession session, @RequestParam Integer current, @RequestParam Integer size) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_ALL)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        User loginUser = userService.getById(session.getAttribute("userId").toString());
        Record record = new Record();
        if (loginUser.getRoleId() != 1) {
            record.setStoreId(loginUser.getStoreId());
            if (loginUser.getStoreId() == 4) {
                record.setUserId(loginUser.getId());
            }
        }
        IPage<Record> page = this.recordService.getRecordList(new Page<>(current, size), record);
        return JsonUtil.listToLayJson(FIELDS, page.getRecords(), page.getTotal());
    }

    @GetMapping("/getServiceList")
    @ApiOperation("获取待处理售后信息列表接口")
    public String getServiceList(HttpSession session, @RequestParam Integer current, @RequestParam Integer size) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        User loginUser = userService.getById(session.getAttribute("userId").toString());
        Record record = new Record();
        if (loginUser.getRoleId() != 1) {
            record.setStoreId(loginUser.getStoreId());
        }
        IPage<Record> page = this.recordService.getServiceList(new Page<>(current, size), record);
        return JsonUtil.listToLayJson(FIELDS, page.getRecords(), page.getTotal());
    }

    @PostMapping("/addOrUpdateRecord")
    @ApiOperation("添加或修改销售记录接口")
    public String addOrUpdateRecord(HttpSession session, @RequestBody Record record) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_ALL)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        User loginUser = userService.getById(session.getAttribute("userId").toString());
        Long goodsId = record.getGoodsId();
        Long storeId = record.getStoreId();
        if (storeId == null) {
            storeId = loginUser.getStoreId();
        }
        if (record.getId() == null) {
            if (!UtilTools.checkLogin(session, Constants.ROLE_SALESMAN)) {
                return ResJson.NO_LOGIN_RETURN_JSON;
            }
            QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("goods_id", goodsId);
            queryWrapper.eq("store_id", storeId);
            Inventory inventory = this.inventoryService.getOne(queryWrapper);
            if (inventory == null || inventory.getInventory() == 0) {
                return ResJson.FAIL_RETURN_JSON;
            }
            record.setRecordStatus(0);
            record.setStoreId(storeId);
            record.setUserId(loginUser.getId());
            boolean flag = this.recordService.saveOrUpdate(record);
            if (flag) {
                UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("goods_id", goodsId);
                updateWrapper.eq("store_id", storeId);
                updateWrapper.set("inventory", inventory.getInventory() - 1);
                boolean update = this.inventoryService.update(updateWrapper);
                if (update) {
                    return ResJson.SUCCESS_RETURN_JSON;
                } else {
                    this.recordService.removeById(record.getId());
                    return ResJson.FAIL_RETURN_JSON;
                }
            } else {
                return ResJson.FAIL_RETURN_JSON;
            }
        } else {
            boolean flag = this.recordService.saveOrUpdate(record);
            if (flag) {
                return ResJson.SUCCESS_RETURN_JSON;
            } else {
                return ResJson.FAIL_RETURN_JSON;
            }
        }
    }

    @PostMapping("/passService")
    @ApiOperation("通过售后申请接口")
    public String passService(HttpSession session, @RequestBody Record record) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        if (record.getRecordStatus() == 1) {
            QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", record.getStoreId());
            queryWrapper.eq("goods_id", record.getGoodsId());
            Inventory inventory = this.inventoryService.getOne(queryWrapper);
            if (inventory.getInventory() < 1) {
                return "{\"code\":500,\"msg\":\"请检查库存数量\"}";
            }
            UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("inventory", inventory.getInventory() - 1);
            updateWrapper.eq("id", inventory.getId());
            boolean flag = this.inventoryService.update(updateWrapper);
            if (flag) {
                record.setRecordStatus(3);
                flag = this.recordService.saveOrUpdate(record);
                if (flag) {
                    return ResJson.SUCCESS_RETURN_JSON;
                } else {
                    updateWrapper = new UpdateWrapper<>();
                    updateWrapper.set("inventory", inventory.getInventory());
                    updateWrapper.eq("id", inventory.getId());
                    return ResJson.FAIL_RETURN_JSON;
                }
            } else {
                return ResJson.FAIL_RETURN_JSON;
            }
        } else if (record.getRecordStatus() == 2) {
            record.setRecordStatus(4);
            boolean flag = this.recordService.saveOrUpdate(record);
            if (flag) {
                return ResJson.SUCCESS_RETURN_JSON;
            } else {
                return ResJson.FAIL_RETURN_JSON;
            }
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }
}
