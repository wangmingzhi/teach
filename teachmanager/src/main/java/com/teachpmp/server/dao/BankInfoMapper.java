package com.teachpmp.server.dao;

import com.teachpmp.server.entity.BankInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BankInfoMapper extends BaseMapper<BankInfo>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bank_info
     *
     * @mbg.generated Thu Feb 13 17:39:08 CST 2020
     */
    int deleteByPrimaryKey(String bankId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bank_info
     *
     * @mbg.generated Thu Feb 13 17:39:08 CST 2020
     */
    int insert(BankInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bank_info
     *
     * @mbg.generated Thu Feb 13 17:39:08 CST 2020
     */
    BankInfo selectByPrimaryKey(String bankId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bank_info
     *
     * @mbg.generated Thu Feb 13 17:39:08 CST 2020
     */
    List<BankInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bank_info
     *
     * @mbg.generated Thu Feb 13 17:39:08 CST 2020
     */
    int updateByPrimaryKey(BankInfo record);
    BankInfo getBank(BankInfo bankInfo);
    void updateExerciseNum(@Param("bankId") String bankId, @Param("exerciseNum") int num);
}