package com.teachpmp.server.service;

import com.teachpmp.server.dao.ResourceMapper;
import com.teachpmp.server.dao.RoleMapper;
import com.teachpmp.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends AbstractBaseService<Role>{

    @Autowired
    ResourceMapper resourceMapper;

    private final RoleMapper roleMapper;
    @Autowired
    public RoleService(RoleMapper roleMapper) {
        super(roleMapper);
        this.roleMapper = roleMapper;
    }


    public Role getRoleByName(String roleName) {
        return roleMapper.getRoleByName(roleName);
    }

    public int deleteByUserId(String userId){
       return roleMapper.deleteByUserId(userId);
    }

    public int deleteByRoleId(String roleId){
        return roleMapper.deleteByRoleId(roleId);
    }

    public int deleteMenuByRoleId(String roleId){
        return roleMapper.deleteMenuByRoleId(roleId);
    }

    public List<String> getResourceIds(String roleId){
        return roleMapper.getResourceIds(roleId);
    }

    public int addBatch(List<RoleUser> roleUsers){
        return roleMapper.addBatch(roleUsers);
    }

    public int addBatchResource(List<RoleResource> roleResource){
        return roleMapper.addBatchResource(roleResource);
    }

    public List<Tree> getMenu(String userId){
        List<Tree> trees = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if(userId != null){
            ids = roleMapper.getResourceByUser(userId);
            if(ids.size() == 0){
                return trees;
            }
        }
        boolean filter = userId != null & ids.size() >0;
        List<Resource> resourceList = resourceMapper.selectByParentNode("0", null);
        for(Resource resource : resourceList){
            Tree tree = resource.toTree();
            List<Resource> childrenList = resourceMapper.selectByParentNode(resource.getResourceId(), null);
            boolean has = false;
            for(Resource children : childrenList){
                if(!filter){
                    tree.getChildren().add(children.toTree());
                }else if(ids.contains(children.getResourceId())){
                    tree.getChildren().add(children.toTree());
                    has = true;
                }
            }
            if(!filter){
                trees.add(tree);
            }else if(ids.contains(tree.getId()) || has){
                trees.add(tree);
            }
        }
        return trees;
    }
}
