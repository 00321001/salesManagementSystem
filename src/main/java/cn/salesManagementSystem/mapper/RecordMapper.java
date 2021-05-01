package cn.salesManagementSystem.mapper;

import cn.salesManagementSystem.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品销售记录Mapper层接口
 * @date 2021-05-01 10:35
 **/

public interface RecordMapper extends BaseMapper<Record> {
    IPage<Record> getRecordList(IPage<Record> page, Record record);

    IPage<Record> getServiceList(IPage<Record> page, Record record);
}
