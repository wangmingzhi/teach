package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.BankService;
import com.teachpmp.server.service.ExerciseService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController("BankController")
@RequestMapping(value = "/api/admin/bank")
public class BankController extends BaseApiController{

    @Autowired
    BankService bankService;
    @Autowired
    ExerciseService exerciseService;

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody BankInfo model) {
        PageInfo<BankInfo> page = bankService.page(model, model.getPageIndex(), model.getPageSize(), "");
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<BankInfo> select(@PathVariable String id) {
        BankInfo bankInfo = bankService.selectByPrimaryKey(id);
        return RestResponse.ok(bankInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<BankInfo> edit(@RequestBody BankInfo bankInfo) {
        bankInfo.setBankNo(bankInfo.getTrainType()+"_"+bankInfo.getExerciseType()+"_"+ bankInfo.getOrderNo());
        if (bankInfo.getBankId() == null) {  //create
            BankInfo existBank = bankService.getBank(bankInfo);
            if (null != existBank) {
                return new RestResponse<>(2, "题库已存在");
            }
        }
        if (bankInfo.getBankId() == null) {
            bankInfo.setBankId(CommonUtils.getUUID());
            bankInfo.setCreateTime(new Date());
            bankInfo.setCreateUser(getCurrentUserName());
            bankService.insert(bankInfo);
        } else {
            bankInfo.setUpdateTime(new Date());
            bankInfo.setUpdateUser(getCurrentUserName());
            bankService.updateByPrimaryKey(bankInfo);
        }
        return RestResponse.ok(bankInfo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String id) {
        bankService.deleteByPrimaryKey(id);
        exerciseService.deleteByBankId(id);
        return RestResponse.ok();
    }
}
