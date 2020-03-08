package com.teachpmp.server.configuration.spring.security;


import com.teachpmp.server.context.WebContext;
import com.teachpmp.server.entity.enums.RoleEnum;
import com.teachpmp.server.entity.enums.UserStatusEnum;
import com.teachpmp.server.service.AuthenticationService;
import com.teachpmp.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebContext webContext;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = authentication.getName();
        String password = (String) authentication.getCredentials();

        com.teachpmp.server.entity.User user = userService.getUserByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        boolean result = authenticationService.authUser(user, account, password);
        if (!result) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getIsEnable());
        if (UserStatusEnum.Disable == userStatusEnum) {
            throw new LockedException("用户被禁用");
        }

        webContext.setCurrentUser(user);

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ADMIN.getName()));

        User authUser = new User(user.getAccount(), user.getPassword(), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(), authUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
