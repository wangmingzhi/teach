package com.teachpmp.server.entity;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table tb_resource
 *
 * @mbg.generated do_not_delete_during_merge Mon Feb 17 14:20:53 CST 2020
 */
public class Resource {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.resource_id
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String resourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.resource_name
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String resourceName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.title
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.path
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String path;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.icon
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String icon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.parent_node
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String parentNode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.create_user
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.create_time
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.update_user
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private String updateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_resource.update_time
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.resource_id
     *
     * @return the value of tb_resource.resource_id
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.resource_id
     *
     * @param resourceId the value for tb_resource.resource_id
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.resource_name
     *
     * @return the value of tb_resource.resource_name
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.resource_name
     *
     * @param resourceName the value for tb_resource.resource_name
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.title
     *
     * @return the value of tb_resource.title
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.title
     *
     * @param title the value for tb_resource.title
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.path
     *
     * @return the value of tb_resource.path
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.path
     *
     * @param path the value for tb_resource.path
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.icon
     *
     * @return the value of tb_resource.icon
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.icon
     *
     * @param icon the value for tb_resource.icon
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.parent_node
     *
     * @return the value of tb_resource.parent_node
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getParentNode() {
        return parentNode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.parent_node
     *
     * @param parentNode the value for tb_resource.parent_node
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setParentNode(String parentNode) {
        this.parentNode = parentNode == null ? null : parentNode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.create_user
     *
     * @return the value of tb_resource.create_user
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.create_user
     *
     * @param createUser the value for tb_resource.create_user
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.create_time
     *
     * @return the value of tb_resource.create_time
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.create_time
     *
     * @param createTime the value for tb_resource.create_time
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.update_user
     *
     * @return the value of tb_resource.update_user
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.update_user
     *
     * @param updateUser the value for tb_resource.update_user
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_resource.update_time
     *
     * @return the value of tb_resource.update_time
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_resource.update_time
     *
     * @param updateTime the value for tb_resource.update_time
     *
     * @mbg.generated Mon Feb 17 14:20:53 CST 2020
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Tree toTree(){
        Tree tree = new Tree();
        tree.setId(this.getResourceId());
        tree.setLabel(this.getTitle());
        tree.setName(this.getResourceName());
        return tree;
    }
}