package cn.salesManagementSystem.service;

import cn.salesManagementSystem.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户管理相关接口Service层接口
 * @date 2021-02-24 00:19
 **/
public interface IUserService extends IService<User> {
    /**
     * 查询用户列表的Service层接口
     * @param page 分页参数
     * @param roleId 操作者角色id
     * @param storeId 操作者所属门店id
     * @return 分好页的查询结果
     */
    List<User> getUserList(IPage<User> page, Long roleId, Long storeId);

    /**
     * 添加用户的Service层方法
     * @param user 存储数据的用户实体类
     * @return 受影响行数
     */
    boolean addUser(User user);
}
