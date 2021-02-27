package cn.salesManagementSystem.service;

import cn.salesManagementSystem.entity.Store;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 门店管理相关接口Service层接口
 * @date 2021-02-24 15:39
 **/
public interface IStoreService extends IService<Store> {
    /**
     * 删除门店的Service层接口
     * @param id 门店id
     * @return 是否成功
     */
    Boolean deleteStore(Long id);
}
