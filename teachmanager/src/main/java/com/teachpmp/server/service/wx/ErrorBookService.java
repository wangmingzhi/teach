package com.teachpmp.server.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachpmp.server.dao.ErrorBookMapper;
import com.teachpmp.server.entity.*;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.AbstractBaseService;
import com.teachpmp.server.service.ClassService;
import com.teachpmp.server.service.ExerciseService;
import com.teachpmp.server.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorBookService extends AbstractBaseService{

    @Autowired
    ClassService classService;
    @Autowired
    ExamService examService;
    @Autowired
    ExerciseService exerciseService;

    private final ErrorBookMapper errorBookMapper;

    @Autowired
    public ErrorBookService(ErrorBookMapper errorBookMapper) {
        super(errorBookMapper);
        this.errorBookMapper = errorBookMapper;
    }

    public void saveError(Student student, BankInfo bankInfo, StudentAnswer answer){
        ErrorBook errorBook = new ErrorBook();
        errorBook.setErrorId(CommonUtils.getUUID());
        if(student != null){
            errorBook.setStudentId(student.getStudentId());
            errorBook.setStudentName(student.getStudentName());
        }
        errorBook.setTrainType(bankInfo.getTrainType());
        errorBook.setExerciseType(bankInfo.getExerciseType());
        errorBook.setBankId(bankInfo.getBankId());
        errorBook.setBankName(bankInfo.getBankName());
        errorBook.setExcerId(answer.getExcerId());
        errorBook.setOrderNo(answer.getOrderNo());
        errorBook.setSelectionAnswer(answer.getSelectionAnswer());
        errorBook.setIsRemove("0");
        insert(errorBook);
    }

    public void removeError(String studentId, String excerId){
        errorBookMapper.removeError(studentId, excerId);
    }

    public void removeRight(String studentId){
        List<String> exercises = examService.getRightExercise(studentId);
        if(exercises != null && exercises.size() >0){
            errorBookMapper.removeRight(studentId, exercises.toArray(new String[0]));
        }
        examService.restExercise(studentId, null, true);
    }


    public Integer getErrorCount(String studentId, String trainType){
       return errorBookMapper.getErrorCount(studentId, trainType);
    }

    public RestResponse getErrorExercise(int pageIndex, String studentId, String trainType){
        PageInfo<ErrorBook> pageInfo = PageHelper.startPage(pageIndex, 1, "").doSelectPageInfo(() ->
                errorBookMapper.getErrorExercise(studentId, trainType)
        );
        if(pageInfo.getTotal() == 0){
            return RestResponse.fail(0, "暂无数据");
        }
        if(pageIndex > pageInfo.getTotal()){
            return RestResponse.fail(0, "答题结束");
        }
        ErrorBook errorBook = pageInfo.getList().get(0);
        Exercise exercise = exerciseService.selectByPrimaryKey(errorBook.getExcerId());
        exercise.setExerciseNum((int) pageInfo.getTotal());
        exercise.setOrderNo(pageIndex);
        String answer = examService.getAnswer(studentId, exercise.getBankId(), exercise.getExcerId(), true);
        exercise.setUserSelect(answer);
        return  RestResponse.ok(exercise);
    }
}
