package com.teachpmp.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.teachpmp.server.entity.base.BasePage;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table tb_exercise_info
 *
 * @mbg.generated do_not_delete_during_merge Fri Feb 14 18:30:57 CST 2020
 */
public class Exercise extends BankInfo{


    public Exercise() {
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.excer_id
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    private String excerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.bank_id
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    private String bankId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.order_no
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "习题序号", orderNum = "0")
    private Integer orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.content
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "习题内容", orderNum = "1")
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.selection_a
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "A选项", orderNum = "2")
    private String selectionA;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.selection_b
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "B选项", orderNum = "3")
    private String selectionB;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.selection_c
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "C选项", orderNum = "4")
    private String selectionC;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.selection_d
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "D选项", orderNum = "5")
    private String selectionD;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.selection_correct
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "正确答案", orderNum = "6")
    private String selectionCorrect;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.answer
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "解析", orderNum = "7")
    private String answer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.own_domain
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "所属领域", orderNum = "8")
    private String ownDomain;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.own_process
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    @Excel(name = "所属过程", orderNum = "9")
    private String ownProcess;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.create_user
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.create_time
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.update_user
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    private String updateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_exercise_info.update_time
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.excer_id
     *
     * @return the value of tb_exercise_info.excer_id
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getExcerId() {
        return excerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.excer_id
     *
     * @param excerId the value for tb_exercise_info.excer_id
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setExcerId(String excerId) {
        this.excerId = excerId == null ? null : excerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.bank_id
     *
     * @return the value of tb_exercise_info.bank_id
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.bank_id
     *
     * @param bankId the value for tb_exercise_info.bank_id
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.order_no
     *
     * @return the value of tb_exercise_info.order_no
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.order_no
     *
     * @param orderNo the value for tb_exercise_info.order_no
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo == null ? null : orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.content
     *
     * @return the value of tb_exercise_info.content
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.content
     *
     * @param content the value for tb_exercise_info.content
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.selection_a
     *
     * @return the value of tb_exercise_info.selection_a
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getSelectionA() {
        return selectionA;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.selection_a
     *
     * @param selectionA the value for tb_exercise_info.selection_a
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setSelectionA(String selectionA) {
        this.selectionA = selectionA == null ? null : selectionA.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.selection_b
     *
     * @return the value of tb_exercise_info.selection_b
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getSelectionB() {
        return selectionB;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.selection_b
     *
     * @param selectionB the value for tb_exercise_info.selection_b
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setSelectionB(String selectionB) {
        this.selectionB = selectionB == null ? null : selectionB.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.selection_c
     *
     * @return the value of tb_exercise_info.selection_c
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getSelectionC() {
        return selectionC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.selection_c
     *
     * @param selectionC the value for tb_exercise_info.selection_c
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setSelectionC(String selectionC) {
        this.selectionC = selectionC == null ? null : selectionC.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.selection_d
     *
     * @return the value of tb_exercise_info.selection_d
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getSelectionD() {
        return selectionD;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.selection_d
     *
     * @param selectionD the value for tb_exercise_info.selection_d
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setSelectionD(String selectionD) {
        this.selectionD = selectionD == null ? null : selectionD.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.selection_correct
     *
     * @return the value of tb_exercise_info.selection_correct
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getSelectionCorrect() {
        return selectionCorrect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.selection_correct
     *
     * @param selectionCorrect the value for tb_exercise_info.selection_correct
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setSelectionCorrect(String selectionCorrect) {
        this.selectionCorrect = selectionCorrect == null ? null : selectionCorrect.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.answer
     *
     * @return the value of tb_exercise_info.answer
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.answer
     *
     * @param answer the value for tb_exercise_info.answer
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.own_domain
     *
     * @return the value of tb_exercise_info.own_domain
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getOwnDomain() {
        return ownDomain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.own_domain
     *
     * @param ownDomain the value for tb_exercise_info.own_domain
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setOwnDomain(String ownDomain) {
        this.ownDomain = ownDomain == null ? null : ownDomain.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.own_process
     *
     * @return the value of tb_exercise_info.own_process
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getOwnProcess() {
        return ownProcess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.own_process
     *
     * @param ownProcess the value for tb_exercise_info.own_process
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setOwnProcess(String ownProcess) {
        this.ownProcess = ownProcess == null ? null : ownProcess.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.create_user
     *
     * @return the value of tb_exercise_info.create_user
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.create_user
     *
     * @param createUser the value for tb_exercise_info.create_user
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.create_time
     *
     * @return the value of tb_exercise_info.create_time
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.create_time
     *
     * @param createTime the value for tb_exercise_info.create_time
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.update_user
     *
     * @return the value of tb_exercise_info.update_user
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.update_user
     *
     * @param updateUser the value for tb_exercise_info.update_user
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_exercise_info.update_time
     *
     * @return the value of tb_exercise_info.update_time
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_exercise_info.update_time
     *
     * @param updateTime the value for tb_exercise_info.update_time
     *
     * @mbg.generated Fri Feb 14 18:30:57 CST 2020
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private String userSelect;

    public String getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(String userSelect) {
        this.userSelect = userSelect;
    }
}