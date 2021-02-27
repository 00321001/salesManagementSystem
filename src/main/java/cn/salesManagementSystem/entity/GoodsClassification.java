package cn.salesManagementSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 商品分类实体类
 * @date 2021-02-28 03:12
 **/
@Data
@TableName(value = "t_goods_classification")
@ApiModel(value = "门店实体类")
public class GoodsClassification {
        @TableId(type= IdType.AUTO)
        private Long id;
        private String description;
        private String createTime;
        private String updateTime;
}
