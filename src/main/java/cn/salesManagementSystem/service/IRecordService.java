package cn.salesManagementSystem.service;

import cn.salesManagementSystem.entity.Record;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 销售记录相关接口Service层接口
 * @date 2021-02-24 15:39
 **/

public interface IRecordService extends IService<Record> {
    IPage<Record> getRecordList(IPage<Record> page, Record record);
}
