package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.Goods;
import cn.salesManagementSystem.entity.GoodsClassification;
import cn.salesManagementSystem.service.IGoodsClassificationService;
import cn.salesManagementSystem.service.IGoodsService;
import cn.salesManagementSystem.utils.Constants;
import cn.salesManagementSystem.utils.JsonUtil;
import cn.salesManagementSystem.utils.ResJson;
import cn.salesManagementSystem.utils.UtilTools;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    private IGoodsClassificationService goodsClassificationService;
    @Resource
    private IGoodsService goodsService;

    @ApiOperation("获取商品分类下拉框列表接口")
    @GetMapping("/getGoodsClassificationIdAndDescriptionList")
    public String getGoodsClassificationIdAndDescriptionList() {
        QueryWrapper<GoodsClassification> wrapper = new QueryWrapper<>();
        wrapper.select("id", "description");
        List<GoodsClassification> goodsClassificationList = this.goodsClassificationService.list(wrapper);
        return JsonUtil.listToJson(new String[]{"id", "description"}, goodsClassificationList);
    }

    @ApiOperation("获取商品分类列表接口")
    @GetMapping("/getGoodsClassificationList")
    public String getGoodesClassificationList(HttpSession session, @RequestParam Integer current, @RequestParam Integer size) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        Page<GoodsClassification> page = this.goodsClassificationService.page(new Page<>(current, size));
        List<GoodsClassification> goodsClassifications = page.getRecords();
        return JsonUtil.listToLayJson(FIELDS, goodsClassifications, page.getTotal());
    }

    @ApiOperation("增加或修改商品分类接口")
    @PostMapping("/addOrUpdateGoodsClassification")
    public String addGoodsClassification(HttpSession session, @RequestBody GoodsClassification goodsClassification) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        boolean flag = this.goodsClassificationService.save(goodsClassification);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("删除商品分类接口")
    @PostMapping("/deleteGoodsClassification")
    public String deleteGoodsClassification(HttpSession session, @RequestParam Long id) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("classification_id", id);
        int count = this.goodsService.count(goodsQueryWrapper);
        if (count > 0) {
            return ResJson.FAIL_RETURN_JSON;
        }
        boolean flag = this.goodsClassificationService.removeById(id);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation("批量删除商品分类接口")
    @PostMapping("/multiDeleteGoodsClassification")
    public String multiDeleteGoodsClassification(HttpSession session, @RequestParam Long[] ids) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        //标记是否有分类已经被使用
        boolean isBeUsed = false;
        for (Long id : ids) {
            QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("classification_id", id);
            int count = this.goodsService.count(goodsQueryWrapper);
            if (count > 0) {
                isBeUsed = true;
                break;
            }
        }
        if (isBeUsed) {
            return ResJson.FAIL_RETURN_JSON;
        }
        boolean flag = this.goodsClassificationService.removeByIds(Arrays.asList(ids));
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }
}
