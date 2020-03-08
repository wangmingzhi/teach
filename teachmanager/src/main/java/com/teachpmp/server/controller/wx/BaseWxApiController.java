package com.teachpmp.server.controller.wx;

import com.teachpmp.server.context.WebContext;
import com.teachpmp.server.context.WxContext;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseWxApiController {

    @Autowired
    protected WxContext wxContext;

    protected Student getCurrentStudent() {
        return wxContext.getStudent();
    }

    protected String getStudentId() {
        Student student = getCurrentStudent();
        if(student == null){
            return null;
        }
        return student.getStudentId();
    }

    protected String getStudentName() {
        Student student = getCurrentStudent();
        if(student == null){
            return null;
        }
        return student.getStudentName();
    }
}
