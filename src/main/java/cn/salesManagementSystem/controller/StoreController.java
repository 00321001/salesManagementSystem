package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.Store;
import cn.salesManagementSystem.service.IStoreService;
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
 * @description 门店管理相关Controller层接口
 * @date 2021-02-24 15:42
 **/
@Api(tags = "商店管理相关接口")
@Log4j
@RequestMapping("/store")
@RestController
public class StoreController {

    @Resource
    private IStoreService service;

    private static final String[] FIELDS = new String[]{"id", "name", "description", "createTime", "updateTime"};

    @ApiOperation("获取门店下拉框列表接口")
    @GetMapping("/getStoreIdAndNameList")
    public String getStoreIdAndNameList() {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        List<Store> stores = this.service.list(wrapper);
        return JsonUtil.listToJson(new String[]{"id", "name"}, stores);
    }

    @ApiOperation("获取门店列表接口")
    @GetMapping("/getStoreList")
    public String getStoreList(@RequestParam Integer current, @RequestParam Integer size, HttpSession session){
        if(!UtilTools.checkLogin(session, 1)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        IPage<Store> storeIPage = this.service.page(new Page<Store>(current, size));
        List<Store> storeList = storeIPage.getRecords();
        return JsonUtil.listToLayJson(FIELDS, storeList, storeIPage.getTotal());
    }

    @ApiOperation("添加或修改门店接口")
    @PostMapping("/addOrUpdateStore")
    public String addOrUpdateStore(@RequestBody Store store, HttpSession session){
        if(!UtilTools.checkLogin(session, 1)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        boolean flag = this.service.saveOrUpdate(store);
        if(flag){
            return ResJson.SUCCESS_RETURN_JSON;
        }else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("删除门店接口")
    @PostMapping("/deleteStore")
    public String deleteStore(@RequestParam Long id, HttpSession session){
        if(!UtilTools.checkLogin(session, 1)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        boolean flag = this.service.deleteStore(id);
        if(flag){
            return ResJson.SUCCESS_RETURN_JSON;
        }else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("批量删除门店接口")
    @PostMapping("/multiDeleteStore")
    private String multiDeleteStore(@RequestParam Long[] ids, HttpSession session){
        if(!UtilTools.checkLogin(session, 1)){
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        int deleteNum = 0;
        for (Long id: ids) {
            boolean flag = this.service.deleteStore(id);
            if(flag){
                deleteNum ++;
            }
        }
        if(deleteNum == 0){
            return ResJson.FAIL_RETURN_JSON;
        }
        return ResJson.SUCCESS_RETURN_JSON.substring(0,ResJson.SUCCESS_RETURN_JSON.length() - 1) + ",\"data\":{\"deleteNum\":\"" + deleteNum + "\"}}";
    }
}
