package com.teachpmp.server.context;

import com.teachpmp.server.entity.User;
import com.teachpmp.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WebContext {

    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();
    @Autowired
    private UserService userService;


    public void setCurrentUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public User getCurrentUser() {
        User user = USER_THREAD_LOCAL.get();
        if (null != user) {
            return user;
        } else {
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                return null;
            }
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (null == springUser) {
                return null;
            }
            user = userService.getUserByAccount(springUser.getUsername());
            USER_THREAD_LOCAL.set(user);
            return user;
        }
    }

    /**
     * 删除当前线程的工作单元，建议放在finally中调用，避免内存泄漏
     */
    public  void clean() {
        USER_THREAD_LOCAL.remove();
    }
}
