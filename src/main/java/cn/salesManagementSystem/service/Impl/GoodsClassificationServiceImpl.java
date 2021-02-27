package cn.salesManagementSystem.service.Impl;

import cn.salesManagementSystem.entity.GoodsClassification;
import cn.salesManagementSystem.mapper.GoodsClassificationMapper;
import cn.salesManagementSystem.service.IGoodsClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品分类Service层实现类
 * @date 2021-02-28 03:15
 **/
@Service
public class GoodsClassificationServiceImpl extends ServiceImpl<GoodsClassificationMapper, GoodsClassification> implements IGoodsClassificationService {
    @Resource
    private GoodsClassificationMapper goodsClassificationMapper;
}
