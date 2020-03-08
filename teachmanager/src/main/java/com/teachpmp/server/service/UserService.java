package com.teachpmp.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachpmp.server.dao.UserMapper;
import com.teachpmp.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AbstractBaseService<User>{

    private final UserMapper baseMapper;
    @Autowired
    public UserService(UserMapper userMapper) {
        super(userMapper);
        this.baseMapper = userMapper;
    }


    public User getUserByAccount(String account) {
        return baseMapper.getUserByAccount(account);
    }




}
