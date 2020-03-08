package com.teachpmp.server.service;

import com.teachpmp.server.dao.ClassInfoMapper;
import com.teachpmp.server.dao.RoleMapper;
import com.teachpmp.server.entity.ClassInfo;
import com.teachpmp.server.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService extends AbstractBaseService<ClassInfo>{

    private final ClassInfoMapper classInfoMapper;
    @Autowired
    public ClassService(ClassInfoMapper classInfoMapper) {
        super(classInfoMapper);
        this.classInfoMapper = classInfoMapper;
    }


    public ClassInfo getClassByName(String areaType, String className) {
        return classInfoMapper.getClassByName(areaType, className);
    }

}
