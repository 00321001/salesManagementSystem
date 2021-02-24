package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.Store;
import cn.salesManagementSystem.service.IStoreService;
import cn.salesManagementSystem.utils.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 门店管理相关Controller层接口
 * @date 2021-02-24 15:42
 **/
@Api(value = "商店管理相关接口")
@Log4j
@RequestMapping("/Store")
@RestController
public class StoreController {

    @Resource
    private IStoreService service;

    @GetMapping("getStoreIdAndNameList")
    public String getStoreIdAndNameList() {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        List<Store> stores = this.service.list(wrapper);
        return JsonUtil.listToJson(new String[]{"id", "name"}, stores);
    }
}
