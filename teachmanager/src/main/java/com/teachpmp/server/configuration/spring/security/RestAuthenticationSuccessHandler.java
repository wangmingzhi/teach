package com.teachpmp.server.configuration.spring.security;

import com.teachpmp.server.entity.base.SystemCode;
import com.teachpmp.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 登录成功返回
 *
 * @author alvis
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User springUser = (User) authentication.getPrincipal();
        com.teachpmp.server.entity.User user = userService.getUserByAccount(springUser.getUsername());
//        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
//        userEventLog.setContent(user.getUserName() + " 登录系统");
//        eventPublisher.publishEvent(new UserEvent(userEventLog));
        com.teachpmp.server.entity.User newUser = new com.teachpmp.server.entity.User();
        newUser.setUserName(user.getUserName());
//        newUser.setImagePath(user.getImagePath());
        RestUtil.response(response, SystemCode.OK.getCode(), SystemCode.OK.getMessage(), newUser);
    }
}
