package cn.salesManagementSystem.mapper;

import cn.salesManagementSystem.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品关联Mapper层接口
 * @date 2021-02-28 01:31
 **/
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 分页查询商品列表的Mapper层接口
     * @param page 分页参数
     * @return 分好页的查询结果
     */
    IPage<Goods> getGoodsList(IPage<Goods> page);

    /**
     * 删除商品的Mapper层方法
     * @param id 商品id
     * @return 受影响行数
     */
    Integer deleteGoods(Long id);
}
