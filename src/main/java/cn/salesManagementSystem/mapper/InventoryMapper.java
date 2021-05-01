package cn.salesManagementSystem.mapper;

import cn.salesManagementSystem.entity.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品库存Mapper层接口
 * @date 2021-04-30 22:19
 **/

public interface InventoryMapper extends BaseMapper<Inventory> {
    IPage<Inventory> getInventoryList(Integer storeId, IPage<Inventory> page);

    List<Inventory> getInventoryList(Integer storeId);
}
