package cn.salesManagementSystem.service;

import cn.salesManagementSystem.entity.Goods;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品管理Service层接口
 * @date 2021-02-28 01:35
 **/
public interface IGoodsService extends IService<Goods> {
    IPage<Goods> getGoodsList(IPage<Goods> page);

    /**
     * 删除商品的Service层接口
     *
     * @param id 商品id
     * @return 是否成功
     */
    boolean deleteGoods(Long id);
}
