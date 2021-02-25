package cn.salesManagementSystem.service.Impl;

import cn.salesManagementSystem.entity.User;
import cn.salesManagementSystem.mapper.UserMapper;
import cn.salesManagementSystem.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 用户管理相关接口Service层实现类
 * @date 2021-02-24 00:28
 **/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 查询用户列表的Mapper层接口
     *
     * @param page    分页参数
     * @param roleId  操作者角色id
     * @param storeId 操作者所属门店id
     * @return 分好页的查询结果
     */
    @Override
    public IPage<User> getUserList(IPage<User> page, Long roleId, Long storeId) {
        return this.userMapper.getUserList(page, roleId, storeId);
    }

    /**
     * 添加用户的Service层方法
     *
     * @param user 存储数据的用户实体类
     * @return 受影响行数
     */
    @Override
    public boolean addUser(User user) {
        return this.userMapper.addUser(user) == 1;
    }
}
