package cn.salesManagementSystem.service.Impl;

import cn.salesManagementSystem.entity.Goods;
import cn.salesManagementSystem.mapper.GoodsMapper;
import cn.salesManagementSystem.service.IGoodsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品管理Service层实现类
 * @date 2021-02-28 01:36
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public IPage<Goods> getGoodsList(IPage<Goods> page) {
        return this.goodsMapper.getGoodsList(page);
    }

    /**
     * 删除商品的Service层实现
     *
     * @param id 商品id
     * @return 是否成功
     */
    @Override
    public boolean deleteGoods(Long id) {
        return this.goodsMapper.deleteGoods(id) == 1;
    }
}
