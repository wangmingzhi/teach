package com.teachpmp.server.configuration.spring.security;

import com.teachpmp.server.configuration.property.SystemConfig;
import com.teachpmp.server.entity.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocation implements FilterInvocationSecurityMetadataSource {


    @Autowired
    SystemConfig systemConfig;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<String> ignoreUrls = systemConfig.getSecurityIgnoreUrls();
        if(ignoreUrls != null){
            for(String conf: ignoreUrls){
                if (antPathMatcher.match(conf, requestUrl)) {
                    return SecurityConfig.createList("ROLE_NONE");
                }
            }
        }

//        List<Menu> menus = menuService.getAllMenusWithRole();
//        for (Menu menu : menus) {
//            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
//                List<Role> roles = menu.getRoles();
//                String[] str = new String[roles.size()];
//                for (int i = 0; i < roles.size(); i++) {
//                    str[i] = roles.get(i).getName();
//                }
//                return SecurityConfig.createList(str);
//            }
//        }


        if (antPathMatcher.match("/api/admin/**", requestUrl)) {
            return SecurityConfig.createList(RoleEnum.ADMIN.getName());
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
