package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.Dictionary;
import com.teachpmp.server.entity.Role;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.DicService;
import com.teachpmp.server.service.RoleService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController("DicController")
@RequestMapping(value = "/api/admin/dic")
public class DicController extends BaseApiController{

    @Autowired
    DicService dicService;

    @RequestMapping(value = "/page/type", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody Dictionary model) {
        PageInfo<Dictionary> page = dicService.page(model, model.getPageIndex(), model.getPageSize(), "order_no");
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageDic(@RequestBody Dictionary model) {
        PageInfo<Dictionary> page = dicService.pageDic(model);
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<Dictionary> select(@PathVariable String id) {
        Dictionary role = dicService.selectByPrimaryKey(id);
        return RestResponse.ok(role);
    }

    @RequestMapping(value = "/editType", method = RequestMethod.POST)
    public RestResponse<Dictionary> editType(@RequestBody Dictionary dictionary) {
        if (dictionary.getDictionaryId() == null) {  //create
            Dictionary existDictionary = dicService.getDicByTypeCode(dictionary.getTypeCode());
            if (null != existDictionary) {
                return new RestResponse<>(2, "类型编码已存在");
            }
        }
        if (dictionary.getDictionaryId() == null) {
            dictionary.setDictionaryId(CommonUtils.getUUID());
            dictionary.setCreateTime(new Date());
            dictionary.setCreateUser(getCurrentUserName());
            dicService.insert(dictionary);
        } else {
            dictionary.setUpdateTime(new Date());
            dictionary.setUpdateUser(getCurrentUserName());
            Dictionary old = dicService.selectByPrimaryKey(dictionary.getDictionaryId());
            dicService.updateType(old.getTypeCode(), dictionary.getTypeCode(), dictionary.getTypeName());
            dicService.updateTypeByPrimaryKey(dictionary);
        }
        return RestResponse.ok(dictionary);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<Dictionary> editDic(@RequestBody Dictionary dictionary) {
        if (dictionary.getDictionaryId() == null) {  //create
            Dictionary existDictionary = dicService.getDicByDicCode(dictionary.getTypeCode(), dictionary.getDictionaryCode());
            if (null != existDictionary) {
                return new RestResponse<>(2, "字典编码已存在");
            }
        }
        if (dictionary.getDictionaryId() == null) {
            dictionary.setDictionaryId(CommonUtils.getUUID());
            dictionary.setCreateTime(new Date());
            dictionary.setCreateUser(getCurrentUserName());
            dicService.insert(dictionary);
        } else {
            dictionary.setUpdateTime(new Date());
            dictionary.setUpdateUser(getCurrentUserName());
            dicService.updateByPrimaryKey(dictionary);
        }
        return RestResponse.ok(dictionary);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String id) {
        dicService.deleteByPrimaryKey(id);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/deleteType/{type}", method = RequestMethod.POST)
    public RestResponse deleteType(@PathVariable String type) {
        dicService.deleteType(type);
        return RestResponse.ok();
    }
}
