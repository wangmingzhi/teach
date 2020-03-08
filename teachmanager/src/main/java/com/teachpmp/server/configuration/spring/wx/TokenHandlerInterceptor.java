package com.teachpmp.server.configuration.spring.wx;

import com.teachpmp.server.configuration.spring.security.RestUtil;
import com.teachpmp.server.context.WxContext;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.User;
import com.teachpmp.server.entity.UserToken;
import com.teachpmp.server.entity.base.SystemCode;
import com.teachpmp.server.service.StudentService;
import com.teachpmp.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TokenHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private StudentService studentService;
    @Autowired
    private WxContext wxContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            RestUtil.response(response, SystemCode.UNBIND);
            return false;
        }
        if (token.length() != 32) {
            RestUtil.response(response, SystemCode.UNBIND);
            return false;
        }

        Student student = studentService.selectByPrimaryKey(token);
        if (null == student) {
            RestUtil.response(response, SystemCode.UNBIND);
            return false;
        }

        if(!"1".equals(student.getIsAuthorize())){
            RestUtil.response(response, SystemCode.NOAccess);
            return false;
        }
        Date now = new Date();
        if (student.getExpiryDate() != null && now.before(student.getExpiryDate())) {
            wxContext.setContext(student);
            return true;
        } else {
            RestUtil.response(response, SystemCode.NOExpiry);
            return false;
        }
    }
}
