package cn.salesManagementSystem.service.Impl;

import cn.salesManagementSystem.entity.Inventory;
import cn.salesManagementSystem.mapper.InventoryMapper;
import cn.salesManagementSystem.service.IInventoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品库存Service层实现类
 * @date 2021-02-28 01:36
 **/

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {

    @Override
    public IPage<Inventory> getInventoryList(Integer storeId, IPage<Inventory> page) {
        return this.baseMapper.getInventoryList(storeId, page);
    }
}
