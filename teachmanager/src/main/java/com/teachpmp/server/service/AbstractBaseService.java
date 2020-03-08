package com.teachpmp.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachpmp.server.dao.BaseMapper;
import com.teachpmp.server.entity.Role;
import com.teachpmp.server.entity.User;

import java.util.List;

public abstract class AbstractBaseService<T> implements BaseService<T>{

    BaseMapper<T> baseMapper;

    public AbstractBaseService(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    public int insert(T record){
        return baseMapper.insert(record);
    }

    public int deleteByPrimaryKey(String id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    public T selectByPrimaryKey(String id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    public PageInfo<T> page(T requestVM, int index, int size, String order) {
        return PageHelper.startPage(index, size, order).doSelectPageInfo(() ->
                baseMapper.page(requestVM)
        );
    }
}
