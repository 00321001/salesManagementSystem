package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.GoodsClassification;
import cn.salesManagementSystem.service.IGoodsClassificationService;
import cn.salesManagementSystem.utils.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品分类Controller层接口
 * @date 2021-02-28 03:17
 **/

@RestController
@RequestMapping(value = "/goodsClassification")
@Log4j
@Api(tags = "商品管理相关接口")
public class GoodsClassificationController {

    private static final String[] FIELDS = new String[]{"id", "description", "createTime", "updateTime"};
    @Resource
    private IGoodsClassificationService service;

    @ApiOperation("获取商品分类下拉框列表接口")
    @GetMapping("/getGoodsClassificationIdAndDescriptionList")
    public String getStoreIdAndNameList() {
        QueryWrapper<GoodsClassification> wrapper = new QueryWrapper<>();
        wrapper.select("id", "description");
        List<GoodsClassification> goodsClassificationList = this.service.list(wrapper);
        return JsonUtil.listToJson(new String[]{"id", "description"}, goodsClassificationList);
    }
}
