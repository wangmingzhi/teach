package com.teachpmp.server.configuration.spring.security;

import com.teachpmp.server.context.WebContext;
import com.teachpmp.server.entity.enums.RoleEnum;
import com.teachpmp.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RestDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private WebContext webContext;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.teachpmp.server.entity.User user = userService.getUserByAccount(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username  not found.");
        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ADMIN.getName()));

        webContext.setCurrentUser(user);
        User authUser = new User(user.getUserName(), user.getPassword(), grantedAuthorities);
        return authUser;
    }
}
