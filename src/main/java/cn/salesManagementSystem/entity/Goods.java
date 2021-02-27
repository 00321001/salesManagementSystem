package cn.salesManagementSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品实体类
 * @date 2021-02-28 01:31
 **/

@Data
@TableName(value = "t_goods")
@ApiModel(value = "门店实体类")
public class Goods {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String price;
    private Long classificationId;
    private String createTime;
    private String updateTime;
    @TableField(exist = false)
    private String classification;
}
