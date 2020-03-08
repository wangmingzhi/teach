package com.teachpmp.server.controller.wx;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.Exercise;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.StudentAnswer;
import com.teachpmp.server.entity.base.BasePage;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.BankService;
import com.teachpmp.server.service.ExerciseService;
import com.teachpmp.server.service.wx.ErrorBookService;
import com.teachpmp.server.service.wx.ExamService;
import com.teachpmp.server.service.wx.ScoreService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller("WXExamController")
@RequestMapping(value = "/api/wx/student/exam")
@ResponseBody
public class ExamController extends BaseWxApiController{

    @Autowired
    ExerciseService exerciseService;
    @Autowired
    ExamService examService;
    @Autowired
    BankService bankService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    ErrorBookService errorBookService;

    @RequestMapping(value = "/getBank", method = RequestMethod.POST)
    public RestResponse<List> getBank(BankInfo bankInfo) {
        List<BankInfo> bankInfos = bankService.getBanks(bankInfo);
        if(bankInfos.size() == 0){
            return RestResponse.fail(0, "暂无数据");
        }
        for(BankInfo bankInfo1 : bankInfos){
            bankInfo1.setDoNum(examService.getDoNum(getStudentId(), bankInfo1.getBankId()));
        }
        return  RestResponse.ok(bankInfos);
    }

    @RequestMapping(value = "/getExercise", method = RequestMethod.POST)
    public RestResponse<Exercise> getExercise(Exercise exercise, boolean isErrorBook) {
        if(isErrorBook){
            return errorBookService.getErrorExercise(exercise.getOrderNo(), getStudentId(), exercise.getTrainType());
        }
        Exercise info = exerciseService.getExercise(exercise);
        if(info == null){
            return RestResponse.fail(0, "暂无数据");
        }
        String studentId =  getStudentId();
        if(studentId != null){
           String answer = examService.getAnswer(studentId, info.getBankId(), info.getExcerId(), isErrorBook);
           info.setUserSelect(answer);
        }
        return  RestResponse.ok(info);
    }


    @RequestMapping(value = "/doExercise", method = RequestMethod.POST)
    public RestResponse<Exercise> doExercise(StudentAnswer answer, boolean isErrorBook) {
        Student student =getCurrentStudent();
        if(student != null){
            answer.setAreaType(student.getAreaType());
            answer.setStudentId(student.getStudentId());
            answer.setStudentName(student.getStudentName());
        }
        BankInfo bankInfo = bankService.selectByPrimaryKey(answer.getBankId());
        answer.setTrainType(bankInfo.getTrainType());
        answer.setExerciseType(bankInfo.getExerciseType());
        answer.setBankName(bankInfo.getBankName());
        answer.setCreateTime(new Date());
        answer.setStudentAnswerId(CommonUtils.getUUID());
        answer.setIsErrorBook(isErrorBook ? "1": "0");
        examService.insert(answer);
        if(!isErrorBook){
            //记录错题本
            errorBookService.removeError(getStudentId(), answer.getExcerId());
            if(!"1".equals(answer.getIsRight())){
                errorBookService.saveError(student, bankInfo, answer);
            }
        }
        //答题结束
        if(!isErrorBook){
            int doNum = examService.getDoNum(getStudentId(), answer.getBankId());
            if(bankInfo.getExerciseNum() <= doNum) {
                //记录成绩
                scoreService.calculate(student, bankInfo);
                return RestResponse.fail(0, "答题结束");
            }
        }
        //返回下一题
        Exercise exercise = new Exercise();
        exercise.setBankId(answer.getBankId());
        exercise.setOrderNo(answer.getOrderNo() + 1);
        exercise.setTrainType(answer.getTrainType());
        return getExercise(exercise, isErrorBook);
    }

    @RequestMapping(value = "/restExercise", method = RequestMethod.POST)
    public RestResponse<Exercise> restExercise(String bankId, boolean timeout) {
        //答题超时
        if(timeout){
            Student student =getCurrentStudent();
            BankInfo bankInfo = bankService.selectByPrimaryKey(bankId);
            scoreService.calculate(student, bankInfo);
        }
        examService.restExercise(getStudentId(), bankId, false);
        Exercise exercise = new Exercise();
        exercise.setBankId(bankId);
        exercise.setOrderNo(1);
        return getExercise(exercise, false);
    }

    @RequestMapping(value = "/getErrorCount", method = RequestMethod.POST)
    public RestResponse getErrorCount(String trainType){
        errorBookService.removeRight(getStudentId());
        return  RestResponse.ok(errorBookService.getErrorCount(getStudentId(), trainType));
    }

    @RequestMapping(value = "/deleteError", method = RequestMethod.POST)
    public RestResponse deleteError(String excerId){
        errorBookService.removeError(getStudentId(), excerId);
        return  RestResponse.ok();
    }

    @RequestMapping(value = "/getAswers", method = RequestMethod.POST)
    public RestResponse getAswers(String bankId, int exerciseNum){
        Map result = examService.getAswers(getStudentId(), bankId, exerciseNum);
        return  RestResponse.ok(result);
    }


}
