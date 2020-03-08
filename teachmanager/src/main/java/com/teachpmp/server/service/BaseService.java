package com.teachpmp.server.service;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.entity.Exercise;
import com.teachpmp.server.entity.User;

import java.util.List;

public interface BaseService<T> {

    int insert(T record);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKey(T record);

    T selectByPrimaryKey(String id);

    List<T> selectAll();

    PageInfo<T> page(T requestVM, int index, int size, String order);

}
