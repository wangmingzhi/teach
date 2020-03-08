package com.teachpmp.server.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachpmp.server.dao.ScoreMapper;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.Score;
import com.teachpmp.server.entity.Student;
import com.teachpmp.server.entity.base.BasePage;
import com.teachpmp.server.service.AbstractBaseService;
import com.teachpmp.server.service.ClassService;
import com.teachpmp.server.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScoreService extends AbstractBaseService{

    @Autowired
    ClassService classService;
    @Autowired
    ExamService examService;

    private final ScoreMapper scoreMapper;

    @Autowired
    public ScoreService(ScoreMapper scoreMapper) {
        super(scoreMapper);
        this.scoreMapper = scoreMapper;
    }

    public Score calculate(Student student, BankInfo bankInfo){
        Score score = new Score();
        score.setScoreId(CommonUtils.getUUID());
        if(student != null){
            score.setClassId(student.getClassId());
            if(StringUtils.isNotBlank(student.getClassId())){
                score.setClassName(classService.selectByPrimaryKey(student.getClassId()).getClassName());
            }
            score.setStudentId(student.getStudentId());
            score.setStudentName(student.getStudentName());
        }
        score.setTrainType(bankInfo.getTrainType());
        score.setExerciseType(bankInfo.getExerciseType());
        score.setBankName(bankInfo.getBankName());
        score.setBankId(bankInfo.getBankId());
        score.setRightExerciseNum(examService.getRight(student.getStudentId(), bankInfo.getBankId()));
        score.setExerciseNum(bankInfo.getExerciseNum());
        score.setScore((int)Math.round(score.getRightExerciseNum()*100d/score.getExerciseNum()));
        score.setScoreTime(new Date());
        insert(score);
        return score;
    }

    public PageInfo<Score> getScoreSort(String trainType, BasePage page){
        return PageHelper.startPage(page.getPageIndex(), page.getPageSize(), "").doSelectPageInfo(() ->
                scoreMapper.getScoreSort(trainType)
        );
    }
}
