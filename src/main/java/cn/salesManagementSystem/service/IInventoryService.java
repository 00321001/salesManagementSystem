package cn.salesManagementSystem.service;

import cn.salesManagementSystem.entity.Inventory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品库存Service层接口
 * @date 2021-04-30 22:20
 **/

public interface IInventoryService extends IService<Inventory> {
    IPage<Inventory> getInventoryList(Integer storeId, IPage<Inventory> page);
}
