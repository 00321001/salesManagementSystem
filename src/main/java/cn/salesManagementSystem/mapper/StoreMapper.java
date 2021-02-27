package cn.salesManagementSystem.mapper;

import cn.salesManagementSystem.entity.Store;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户表Mapper层接口
 * @date 2021-02-24 15:39
 **/
public interface StoreMapper extends BaseMapper<Store> {
    /**
     * 删除门店的Mapper层方法
     *
     * @param id 门店id
     * @return 受影响行数
     */
    Integer deleteStore(Long id);
}
