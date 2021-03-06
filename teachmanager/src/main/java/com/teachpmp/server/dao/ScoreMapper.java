package com.teachpmp.server.dao;

import com.teachpmp.server.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_score_info
     *
     * @mbg.generated Thu Feb 27 20:36:06 CST 2020
     */
    int deleteByPrimaryKey(String scoreId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_score_info
     *
     * @mbg.generated Thu Feb 27 20:36:06 CST 2020
     */
    int insert(Score record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_score_info
     *
     * @mbg.generated Thu Feb 27 20:36:06 CST 2020
     */
    Score selectByPrimaryKey(String scoreId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_score_info
     *
     * @mbg.generated Thu Feb 27 20:36:06 CST 2020
     */
    List<Score> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_score_info
     *
     * @mbg.generated Thu Feb 27 20:36:06 CST 2020
     */
    int updateByPrimaryKey(Score record);

    List<Score> getScoreSort(String trainType);
}