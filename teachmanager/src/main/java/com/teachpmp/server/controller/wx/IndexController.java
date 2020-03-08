package com.teachpmp.server.controller.wx;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.Score;
import com.teachpmp.server.entity.Tree;
import com.teachpmp.server.entity.base.BasePage;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.BankService;
import com.teachpmp.server.service.wx.ErrorBookService;
import com.teachpmp.server.service.wx.ExamService;
import com.teachpmp.server.service.wx.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("WXIndexController")
@RequestMapping(value = "/api/wx/student/index")
@ResponseBody
public class IndexController{

    @Autowired
    ExamService examService;
    @Autowired
    BankService bankService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    ErrorBookService errorBookService;

    @RequestMapping(value = "/getType", method = RequestMethod.POST)
    public RestResponse<List> getType(BankInfo bankInfo, String studentId) {
        List<Tree> trees = examService.getType();
        if(trees.size() == 0){
            return RestResponse.fail(0, "暂无数据");
        }
        return  RestResponse.ok(trees);
    }

    @RequestMapping(value = "/getScoreSort", method = RequestMethod.POST)
    public RestResponse<PageInfo> getScoreSort(String trainType, BasePage page){
        return  RestResponse.ok(scoreService.getScoreSort(trainType, page));
    }

}
