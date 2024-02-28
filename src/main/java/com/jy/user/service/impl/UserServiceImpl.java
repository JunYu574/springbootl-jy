package com.jy.user.service.impl;

import com.jy.user.entity.User;
import com.jy.user.mapper.UserMapper;
import com.jy.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: JunYu
 * @Date: 2024/2/22 20:32
 * @Description:
 * @Version: V1.0.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.getUser(user);
    }

}
