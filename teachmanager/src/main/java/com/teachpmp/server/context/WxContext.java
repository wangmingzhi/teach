package com.teachpmp.server.context;

import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.User;
import com.teachpmp.server.entity.UserToken;
import org.springframework.stereotype.Component;

@Component
public class WxContext {
    private final static ThreadLocal<Student> STUDENT_THREAD_LOCAL = new ThreadLocal<>();

    public void setContext(Student student) {
        STUDENT_THREAD_LOCAL.set(student);
    }

    public Student getStudent() {
        return STUDENT_THREAD_LOCAL.get();
    }

}
