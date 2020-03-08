package com.teachpmp.server.configuration.spring.security;

import com.teachpmp.server.entity.User;
import com.teachpmp.server.entity.base.SystemCode;
import com.teachpmp.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication != null){
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User user = userService.getUserByAccount(springUser.getUsername());
//        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
//        userEventLog.setContent(user.getUserName() + " 登出了考试系统");
//        eventPublisher.publishEvent(new UserEvent(userEventLog));
        }
        RestUtil.response(response, SystemCode.OK);
    }
}
