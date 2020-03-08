package com.teachpmp.server.dao;

import com.teachpmp.server.entity.base.BasePage;

import java.util.List;

public interface BaseMapper<T> {

    int insert(T record);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKey(T record);

    T selectByPrimaryKey(String id);

    List<T> selectAll();

    List<T> page(T t);
}
