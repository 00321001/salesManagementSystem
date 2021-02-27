package cn.salesManagementSystem.controller;

import cn.salesManagementSystem.entity.Goods;
import cn.salesManagementSystem.service.IGoodsService;
import cn.salesManagementSystem.utils.Constants;
import cn.salesManagementSystem.utils.JsonUtil;
import cn.salesManagementSystem.utils.ResJson;
import cn.salesManagementSystem.utils.UtilTools;
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
 * @description 商品管理Controller层接口
 * @date 2021-02-28 01:38
 **/

@RestController
@RequestMapping(value = "/goods")
@Log4j
@Api(tags = "商品管理相关接口")
public class GoodsController {
    private static final String[] FIELDS = new String[]{"id", "name", "description", "classificationId", "price", "createTime", "updateTime", "classification"};
    @Resource
    private IGoodsService goodsService;

    @ApiOperation(value = "获取商品列表接口")
    @GetMapping("getGoodsList")
    public String getGoodsList(HttpSession session, @RequestParam Integer current, @RequestParam Integer size) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN + Constants.ROLE_SHOP_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        IPage<Goods> goodsIPage = this.goodsService.getGoodsList(new Page<>(current, size));
        List<Goods> goodsList = goodsIPage.getRecords();
        return JsonUtil.listToLayJson(FIELDS, goodsList, goodsIPage.getTotal());
    }

    @ApiOperation(value = "添加或修改商品接口")
    @PostMapping("addOrUpdateGoods")
    public String addGoods(@RequestBody Goods goods, HttpSession session) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        boolean flag = this.goodsService.saveOrUpdate(goods);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation(value = "删除商品接口")
    @PostMapping("deleteGoods")
    public String deleteGoods(HttpSession session, @RequestParam Long id) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        boolean flag = this.goodsService.deleteGoods(id);
        if (flag) {
            return ResJson.SUCCESS_RETURN_JSON;
        } else {
            return ResJson.FAIL_RETURN_JSON;
        }
    }

    @ApiOperation(value = "批量删除商品接口")
    @PostMapping("/multiDeleteGoods")
    public String multiDeleteStore(HttpSession session, @RequestParam Long[] ids) {
        if (!UtilTools.checkLogin(session, Constants.ROLE_SUPER_ADMIN)) {
            return ResJson.NO_LOGIN_RETURN_JSON;
        }
        int deleteNum = 0;
        for (Long id : ids) {
            boolean flag = this.goodsService.deleteGoods(id);
            if (flag) {
                deleteNum++;
            }
        }
        if (deleteNum == 0) {
            return ResJson.FAIL_RETURN_JSON;
        }
        return ResJson.SUCCESS_RETURN_JSON.substring(0, ResJson.SUCCESS_RETURN_JSON.length() - 1) + ",\"data\":{\"deleteNum\":\"" + deleteNum + "\"}}";
    }
}
