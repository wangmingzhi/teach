package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.Role;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.ClassService;
import com.teachpmp.server.service.ExerciseService;
import com.teachpmp.server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("HomeController")
@RequestMapping(value = "/api/admin/home")
public class HomeController extends BaseApiController {

    @Autowired
    StudentService studentService;
    @Autowired
    ExerciseService exerciseService;

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<Map> index() {
        Map map = new HashMap<>();
        map.put("studentCount", studentService.countStudent());
        map.put("questionCount", exerciseService.countExercise(null));
        return RestResponse.ok(map);
    }
}
