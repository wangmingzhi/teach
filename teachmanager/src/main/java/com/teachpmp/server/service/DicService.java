package com.teachpmp.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachpmp.server.dao.DictionaryMapper;
import com.teachpmp.server.entity.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicService extends AbstractBaseService<Dictionary>{

    private final DictionaryMapper dictionaryMapper;
    @Autowired
    public DicService(DictionaryMapper baseMapper) {
        super(baseMapper);
        this.dictionaryMapper = baseMapper;
    }

    public Dictionary getDicByTypeCode(String typeCode) {
        return dictionaryMapper.getDicByTypeCode(typeCode);
    }

    public Dictionary getDicByDicCode(String typeCode, String dicCode) {
        return dictionaryMapper.getDicByDicCode(typeCode, dicCode);
    }

    public int updateTypeByPrimaryKey(Dictionary record) {
        return dictionaryMapper.updateTypeByPrimaryKey(record);
    }

    public int deleteType(String typeCode) {
        return dictionaryMapper.deleteType(typeCode);
    }
    public PageInfo pageDic(Dictionary model) {
        return PageHelper.startPage(model.getPageIndex(), model.getPageSize(), "order_no").doSelectPageInfo(() ->
                dictionaryMapper.pageDic(model.getTypeCode())
        );
    }

    public int updateType(String oldTypeCode, String typeCode, String typeName){
       return  dictionaryMapper.updateType(oldTypeCode, typeCode, typeName);
    }

    public List<Dictionary> getDictionary(String typeCode){
        return dictionaryMapper.pageDic(typeCode);
    }

}
