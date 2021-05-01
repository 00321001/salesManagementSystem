package cn.salesManagementSystem.service.Impl;

import cn.salesManagementSystem.entity.Record;
import cn.salesManagementSystem.mapper.RecordMapper;
import cn.salesManagementSystem.service.IRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 销售记录Service层实现类
 * @date 2021-05-01 10:48
 **/

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Override
    public IPage<Record> getRecordList(IPage<Record> page, Record record) {
        return this.baseMapper.getRecordList(page, record);
    }
}
