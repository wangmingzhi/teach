package com.teachpmp.server.entity;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private String id;
    private String label;
    private String name;
    private List<Tree> children;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Tree> getChildren() {
        if(children == null){
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tree(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public Tree() {
    }
}
