package cn.salesManagementSystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品库存实体类
 * @date 2021-04-30 22:15
 **/

@Data
@TableName(value = "ref_store_goods")
@ApiModel(value = "商品库存实体类")
public class Inventory {
    private Long id;
    private Long storeId;
    private Long goodsId;
    private Integer inventory;
    private String createTime;
    private String updateTime;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String storeName;
}
