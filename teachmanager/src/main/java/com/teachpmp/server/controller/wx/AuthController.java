package com.teachpmp.server.controller.wx;

import com.teachpmp.server.configuration.property.SystemConfig;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.Tree;
import com.teachpmp.server.entity.UserToken;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.BankService;
import com.teachpmp.server.service.ClassService;
import com.teachpmp.server.service.DicService;
import com.teachpmp.server.service.StudentService;
import com.teachpmp.server.service.wx.ExamService;
import com.teachpmp.server.utils.WxUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Controller("WXStudentAuthController")
@RequestMapping(value = "/api/wx/student/auth")
@ResponseBody
public class AuthController {

    @Autowired
    SystemConfig systemConfig;
    @Autowired
    ClassService classService;
    @Autowired
    DicService dicService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.POST)
    public RestResponse<Student> getStudentInfo(String studentId) {
        Student student = studentService.selectByPrimaryKey(studentId);
        if(student == null){
            return RestResponse.fail(3, "学员已删除");
        }
        setNames(student);
        return RestResponse.ok(student);
    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public RestResponse<Student> bind(@Valid Student student) {
        if(student.getStudentId() != null){
            Student oldsStudent = studentService.selectByPrimaryKey(student.getStudentId());
            oldsStudent.setStudentName(student.getStudentName());
            oldsStudent.setPhoneNo(student.getPhoneNo());
            oldsStudent.setEmail(student.getEmail());
            oldsStudent.setCompany(student.getCompany());
            oldsStudent.setSex(student.getSex());
            student = oldsStudent;
        }
        RestResponse response = studentService.edit(student, student.getWechat());
        if(response.getResponse()!= null){
            setNames((Student) response.getResponse());
        }
        return response;
    }


    @RequestMapping(value = "/checkBind", method = RequestMethod.POST)
    public RestResponse checkBind(@Valid @NotBlank String code) {
        String openid = WxUtil.getOpenId(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), code);
        if (null == openid) {
            return RestResponse.fail(3, "获取微信OpenId失败");
        }
        Student para = new Student();
        para.setOpenid(openid);
        Student student = studentService.selectOne(para);
        if (null != student) {
            setNames(student);
            return RestResponse.ok(student);
        }
        RestResponse restResponse = RestResponse.fail(2, "用户未绑定");
        restResponse.setResponse(para);
        return restResponse;
    }

    private void setNames(Student student){
        if(StringUtils.isNotEmpty(student.getClassId())){
            student.setClassName(classService.selectByPrimaryKey(student.getClassId()).getClassName());
        }
        if(StringUtils.isNotEmpty(student.getAreaType())){
            student.setAreaTypeName(dicService.getDicByDicCode("AREA_TYPE_CODE", student.getAreaType()).getDictionaryName());
        }
        student.setStatusName("1".equals(student.getIsAuthorize()) ? "已授权" : "未授权");
    }


    @RequestMapping(value = "/unBind", method = RequestMethod.POST)
    public RestResponse unBind(@NotBlank String studentId) {
//        Student student = studentService.selectByPrimaryKey(studentId);
//        student.setOpenid(null);
//        studentService.updateByPrimaryKey(student);
        studentService.deleteByPrimaryKey(studentId);
        return RestResponse.ok();
    }

}
