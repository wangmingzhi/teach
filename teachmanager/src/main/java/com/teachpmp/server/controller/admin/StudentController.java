package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.ClassInfo;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.User;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.entity.enums.UserStatusEnum;
import com.teachpmp.server.service.ClassService;
import com.teachpmp.server.service.StudentService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController("StudentController")
@RequestMapping(value = "/api/admin/student")
public class StudentController extends BaseApiController{

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody Student model) {
        PageInfo<Student> page = studentService.page(model, model.getPageIndex(), model.getPageSize(), "create_time desc");
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<Student> select(@PathVariable String id) {
        Student classInfo = studentService.selectByPrimaryKey(id);
        return RestResponse.ok(classInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<Student> edit(@RequestBody Student student) {
        return studentService.edit(student, getCurrentUserName());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String id) {
        studentService.deleteByPrimaryKey(id);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<String> changeStatus(@PathVariable String id) {
        Student student = studentService.selectByPrimaryKey(id);
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(student.getIsAuthorize());
        String newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
        student.setIsAuthorize(newStatus);
        student.setUpdateUser(getCurrentUserName());
        student.setUpdateTime(new Date());
        studentService.updateByPrimaryKey(student);
        return RestResponse.ok(newStatus);
    }
}
