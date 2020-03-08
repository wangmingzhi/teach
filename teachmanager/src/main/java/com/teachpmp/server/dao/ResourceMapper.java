package com.teachpmp.server.dao;

import com.teachpmp.server.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_resource
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    int deleteByPrimaryKey(String resourceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_resource
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    int insert(Resource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_resource
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    Resource selectByPrimaryKey(String resourceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_resource
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    List<Resource> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_resource
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    int updateByPrimaryKey(Resource record);

    List<Resource> selectByParentNode(@Param("parentode")String parentode, @Param("userId")String userId);
}