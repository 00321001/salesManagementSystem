package cn.salesManagementSystem.mapper;

import cn.salesManagementSystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户表Mapper层接口
 * @date 2021-02-24 00:16
 **/
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询用户列表的Mapper层接口
     *
     * @param page    分页参数
     * @param roleId  操作者角色id
     * @param storeId 操作者所属门店id
     * @return 分好页的查询结果
     */
    IPage<User> getUserList(IPage<User> page, Long roleId, Long storeId);

    /**
     * 添加用户的Mapper层方法
     *
     * @param user 存储数据的用户实体类
     * @return 受影响行数
     */
    Integer addUser(User user);
}
