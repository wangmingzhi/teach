package com.teachpmp.server.controller;


import com.teachpmp.server.context.WebContext;
import com.teachpmp.server.entity.User;
import com.teachpmp.server.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseApiController {
    protected final static String DEFAULT_PAGE_SIZE = "10";
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    @Autowired
    protected WebContext webContext;

    protected User getCurrentUser() {
        return webContext.getCurrentUser();
    }

    protected String getCurrentUserName() {
        User user = getCurrentUser();
        if(user == null){
            return null;
        }
        return user.getUserName();
    }
}
