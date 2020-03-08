package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.*;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.RoleService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController("RoleController")
@RequestMapping(value = "/api/admin/role")
public class RoleController extends BaseApiController{

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody Role model) {
        PageInfo<Role> page = roleService.page(model, model.getPageIndex(), model.getPageSize(), "role_code");
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<Role> select(@PathVariable String id) {
        Role role = roleService.selectByPrimaryKey(id);
        return RestResponse.ok(role);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<Role> edit(@RequestBody @Valid Role role) {
        if (role.getRoleId() == null) {  //create
            Role existRole = roleService.getRoleByName(role.getRoleCode());
            if (null != existRole) {
                return new RestResponse<>(2, "角色编码已存在");
            }
        }
        if (role.getRoleId() == null) {
            role.setRoleId(CommonUtils.getUUID());
            role.setCreateTime(new Date());
            role.setCreateUser(getCurrentUserName());
            roleService.insert(role);
        } else {
            Role oldRole = roleService.selectByPrimaryKey(role.getRoleId());
            if(!oldRole.getRoleCode().equals(role.getRoleCode())){
                Role existRole = roleService.getRoleByName(role.getRoleCode());
                if (null != existRole) {
                    return new RestResponse<>(2, "角色编码已存在");
                }
            }
            role.setUpdateTime(new Date());
            role.setUpdateUser(getCurrentUserName());
            roleService.updateByPrimaryKey(role);
        }
        return RestResponse.ok(role);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String id) {
        roleService.deleteByRoleId(id);
        roleService.deleteMenuByRoleId(id);
        roleService.deleteByPrimaryKey(id);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/grantUser/{roleId}", method = RequestMethod.POST)
    public RestResponse grantUser(@PathVariable String roleId, @RequestBody String[]  userIds) {
        roleService.deleteByRoleId(roleId);
        if(userIds != null && userIds.length > 0){
            List<RoleUser> roleUsers = new ArrayList<>();
            for(String userId : userIds){
                RoleUser roleUser = new RoleUser();
                roleUser.setRoleUserId(CommonUtils.getUUID());
                roleUser.setRoleId(roleId);
                roleUser.setUserId(userId);
                roleUser.setUpdateTime(new Date());
                roleUser.setUpdateUser(getCurrentUserName());
                roleUsers.add(roleUser);
            }
            roleService.addBatch(roleUsers);
        }
        return RestResponse.ok();
    }

    @RequestMapping(value = "/grantRole/{userId}", method = RequestMethod.POST)
    public RestResponse grantRole(@PathVariable String userId, @RequestBody String[] roleIds) {
        roleService.deleteByUserId(userId);
        if(roleIds != null && roleIds.length > 0){
            List<RoleUser> roleUsers = new ArrayList<>();
            for(String roleId : roleIds){
                RoleUser roleUser = new RoleUser();
                roleUser.setRoleUserId(CommonUtils.getUUID());
                roleUser.setRoleId(roleId);
                roleUser.setUserId(userId);
                roleUser.setUpdateTime(new Date());
                roleUser.setUpdateUser(getCurrentUserName());
                roleUsers.add(roleUser);
            }
            roleService.addBatch(roleUsers);
        }
        return RestResponse.ok();
    }

    @RequestMapping(value = "/grantMenu/{roleId}", method = RequestMethod.POST)
    public RestResponse grantMenu(@PathVariable String roleId, @RequestBody String[] resourceIds) {
        roleService.deleteMenuByRoleId(roleId);
        if(resourceIds != null && resourceIds.length > 0){
            List<RoleResource> roleResources = new ArrayList<>();
            for(String resourceId : resourceIds){
                RoleResource roleResource = new RoleResource();
                roleResource.setId(CommonUtils.getUUID());
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResources.add(roleResource);
            }
            roleService.addBatchResource(roleResources);
        }
        return RestResponse.ok();
    }

    @RequestMapping(value = "/menuTree/{roleId}", method = RequestMethod.POST)
    public RestResponse<Map> menuTree(@PathVariable String roleId) {
        List<String> checkedMenu = roleService.getResourceIds(roleId);
        List<Tree> menuData = roleService.getMenu(null);
        Map map = new HashMap<>();
        map.put("menuData", menuData);
        map.put("checkedMenu", checkedMenu);
        return RestResponse.ok(map);
    }


    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public RestResponse<List<Tree>> getMenu() {
         User user = getCurrentUser();
         if(user != null){
            String userId = user.getUserId();
            if (user.getAccount().equals("admin")){
                userId = null;
            }
            return RestResponse.ok(roleService.getMenu(userId));
         }
        return RestResponse.ok();
    }
}
