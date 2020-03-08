package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.ClassInfo;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.ClassService;
import com.teachpmp.server.service.StudentService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController("ClassController")
@RequestMapping(value = "/api/admin/class")
public class ClassController extends BaseApiController{

    @Autowired
    StudentService studentService;
    @Autowired
    ClassService classService;

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody ClassInfo model) {
        PageInfo<ClassInfo> page = classService.page(model, model.getPageIndex(), model.getPageSize(), "class_name");
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ClassInfo> select(@PathVariable String id) {
        ClassInfo classInfo = classService.selectByPrimaryKey(id);
        return RestResponse.ok(classInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<ClassInfo> edit(@RequestBody ClassInfo classInfo) {
        if (classInfo.getClassId() == null) {  //create
            ClassInfo existClass = classService.getClassByName(classInfo.getAreaType(), classInfo.getClassName());
            if (null != existClass) {
                return new RestResponse<>(2, "班级已存在");
            }
        }
        if (classInfo.getClassId() == null) {
            classInfo.setClassId(CommonUtils.getUUID());
            classInfo.setCreateTime(new Date());
            classInfo.setCreateUser(getCurrentUserName());
            classService.insert(classInfo);
        } else {
            ClassInfo oldClassInfo = classService.selectByPrimaryKey(classInfo.getClassId());
            if(!(oldClassInfo.equals(classInfo.getAreaType()) && oldClassInfo.getClassName().equals(classInfo.getClassName()))){
                ClassInfo existClass = classService.getClassByName(classInfo.getAreaType(), classInfo.getClassName());
                if (null != existClass) {
                    return new RestResponse<>(2, "班级已存在");
                }
            }
            classInfo.setUpdateTime(new Date());
            classInfo.setUpdateUser(getCurrentUserName());
            classService.updateByPrimaryKey(classInfo);
        }
        return RestResponse.ok(classInfo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String id) {
        studentService.deleteClass(id);
        classService.deleteByPrimaryKey(id);
        return RestResponse.ok();
    }
}
