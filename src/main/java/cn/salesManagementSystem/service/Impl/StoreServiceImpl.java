package cn.salesManagementSystem.service.Impl;

import cn.salesManagementSystem.entity.Store;
import cn.salesManagementSystem.mapper.StoreMapper;
import cn.salesManagementSystem.service.IStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 门店管理相关接口Service层实现
 * @date 2021-02-24 15:40
 **/

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IStoreService {
    @Resource
    private StoreMapper storeMapper;

    /**
     * 删除门店的Service层实现
     *
     * @param id 门店id
     * @return 是否成功
     */
    @Override
    public Boolean deleteStore(Long id) {
        return this.storeMapper.deleteStore(id) == 1;
    }
}
