package cn.salesManagementSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 销售记录实体类
 * @date 2021-05-01 10:29
 **/

@Data
@TableName("t_record")
public class Record {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long storeId;
    private Long userId;
    private Long goodsId;
    private Integer recordStatus;
    private String createTime;
    @TableField(exist = false)
    private String storeName;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String goodsName;
}
