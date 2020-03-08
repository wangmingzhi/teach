package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.Role;
import com.teachpmp.server.entity.User;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.entity.enums.UserStatusEnum;
import com.teachpmp.server.service.AuthenticationService;
import com.teachpmp.server.service.RoleService;
import com.teachpmp.server.service.UserService;
import com.teachpmp.server.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController("UserController")
@RequestMapping(value = "/api/admin/user")
public class UserController extends BaseApiController {

    public static final String DEFUALT_PDW = "lc123456";

    @Autowired
    RoleService roleService;
    @Autowired
    private UserService userService;
//    private UserEventLogService userEventLogService;
    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody User model) {
        PageInfo<User> page = userService.page(model, model.getPageIndex(), model.getPageSize(), "user_name");
        return RestResponse.ok(page);
    }
//
//
//    @RequestMapping(value = "/event/page/list", method = RequestMethod.POST)
//    public RestResponse<PageInfo<UserEventLogVM>> eventPageList(@RequestBody UserEventPageRequestVM model) {
//        PageInfo<UserEventLog> pageInfo = userEventLogService.page(model);
//        PageInfo<UserEventLogVM> page = PageInfoHelper.copyMap(pageInfo, d -> {
//            UserEventLogVM vm = modelMapper.map(d, UserEventLogVM.class);
//            vm.setCreateTime(DateTimeUtil.dateFormat(d.getCreateTime()));
//            return vm;
//        });
//        return RestResponse.ok(page);
//    }
//
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<User> select(@PathVariable String id) {
        User user = userService.selectByPrimaryKey(id);
        return RestResponse.ok(user);
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<User> current() {
        User user = getCurrentUser();
        return RestResponse.ok(user);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody @Valid User user) {
        if (user.getUserId() == null) {  //create
            User existUser = userService.getUserByAccount(user.getAccount());
            if (null != existUser) {
                return new RestResponse<>(2, "账号已存在");
            }
        }
        if (user.getUserId() == null) {
            String encodePwd = authenticationService.pwdEncode(DEFUALT_PDW);
            user.setPassword(encodePwd);
            user.setUserId(CommonUtils.getUUID());
            user.setCreateTime(new Date());
            user.setCreateUser(getCurrentUserName());
            userService.insert(user);
        } else {
            User oldUser = userService.selectByPrimaryKey(user.getUserId());
            if(!oldUser.getAccount().equals(user.getAccount())){
                User existUser = userService.getUserByAccount(user.getAccount());
                if (null != existUser) {
                    return new RestResponse<>(2, "账号已存在");
                }
            }
            user.setUpdateTime(new Date());
            user.setUpdateUser(getCurrentUserName());
            userService.updateByPrimaryKey(user);
        }
        return RestResponse.ok(user);
    }

    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<String> changeStatus(@PathVariable String id) {
        User user = userService.selectByPrimaryKey(id);
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getIsEnable());
        String newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
        user.setIsEnable(newStatus);
        user.setUpdateUser(getCurrentUserName());
        user.setUpdateTime(new Date());
        userService.updateByPrimaryKey(user);
        return RestResponse.ok(newStatus);
    }


    @RequestMapping(value = "/resetPwd/{id}", method = RequestMethod.POST)
    public RestResponse resetPwd(@PathVariable String id) {
        User user = userService.selectByPrimaryKey(id);
        user.setPassword(authenticationService.pwdEncode(DEFUALT_PDW));
        user.setUpdateUser(getCurrentUserName());
        user.setUpdateTime(new Date());
        userService.updateByPrimaryKey(user);
        return RestResponse.ok();
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String id) {
        roleService.deleteByUserId(id);
        userService.deleteByPrimaryKey(id);
        return RestResponse.ok();
    }


}
