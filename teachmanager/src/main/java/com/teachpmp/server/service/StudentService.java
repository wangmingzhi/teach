package com.teachpmp.server.service;

import com.teachpmp.server.dao.ClassInfoMapper;
import com.teachpmp.server.dao.StudentMapper;
import com.teachpmp.server.entity.ClassInfo;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StudentService extends AbstractBaseService<Student>{

    private final StudentMapper studentMapper;
    @Autowired
    public StudentService(StudentMapper studentMapper) {
        super(studentMapper);
        this.studentMapper = studentMapper;
    }


    public Student selectOne(Student student){
        return studentMapper.selectOne(student);
    }

    public int deleteClass(String classId){
        return studentMapper.deleteClass(classId);
    }
    public int countStudent(){
        return studentMapper.countStudent();
    }


    public RestResponse edit(Student student, String user){
        if (student.getStudentId() == null) {  //create
            Student existClass = selectOne(student);
            if (null != existClass) {
                return new RestResponse<>(2, "学员已存在");
            }
        }
        if (student.getStudentId() == null) {
            if(student.getExpiryDate() == null){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 3);
                student.setExpiryDate(calendar.getTime());
            }
            student.setStudentId(CommonUtils.getUUID());
            student.setCreateTime(new Date());
            student.setCreateUser(user);
            insert(student);
        } else {
//            Student oldStudent = selectByPrimaryKey(student.getStudentId());
//            if(!(oldStudent.getWechat().equals(student.getWechat()))){
//                Student existClass = selectOne(student);
//                if (null != existClass) {
//                    return new RestResponse<>(2, "学员已存在");
//                }
//            }
            student.setUpdateTime(new Date());
            student.setUpdateUser(user);
            updateByPrimaryKey(student);
        }
        return RestResponse.ok(student);
    }



}
