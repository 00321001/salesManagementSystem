package cn.salesManagementSystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户实体类
 * @create 2021-02-24 00:03
 **/

@Data
@TableName(value = "t_user")
public class User {
    @TableId
    private Long id;
    private String username;
    private String password;
    private Long roleId;
    private Long storeId;
    private String realName;
    private String email;
    private Integer enableStatus;
    private String createTime;
    private String updateTime;
    @TableField(exist = false)
    private String role;
    @TableField(exist = false)
    private String store;

}
