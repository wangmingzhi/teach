package com.teachpmp.server.service.wx;

import com.teachpmp.server.dao.StudentAnswerMapper;
import com.teachpmp.server.entity.Dictionary;
import com.teachpmp.server.entity.StudentAnswer;
import com.teachpmp.server.entity.Tree;
import com.teachpmp.server.service.AbstractBaseService;
import com.teachpmp.server.service.DicService;
import com.teachpmp.server.utils.DateTimeUtil;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExamService extends AbstractBaseService {

    @Autowired
    DicService dicService;
    private final StudentAnswerMapper answerMapper;

    @Autowired
    public ExamService(StudentAnswerMapper answerMapper) {
        super(answerMapper);
        this.answerMapper = answerMapper;
    }


    public List<Tree> getType() {
        List<Dictionary> tranTypes = dicService.getDictionary("TRAN_TYPE_CODE");
        List<Dictionary> exerTypes = dicService.getDictionary("EXER_TYPE_CODE");
        List<Tree> result = new ArrayList<>();
        for (Dictionary tranType : tranTypes) {
            Tree tranTypeTree = new Tree(tranType.getDictionaryCode(), tranType.getDictionaryCode());
            tranTypeTree.setName(getExamTime(tranType.getDictionaryCode()));
            for (Dictionary exerType : exerTypes) {
                Tree exerTypeTree = new Tree(exerType.getDictionaryCode(), exerType.getDictionaryName());
                tranTypeTree.getChildren().add(exerTypeTree);
            }
            result.add(tranTypeTree);
        }
        return result;
    }

    private String getExamTime(String tranType){
        Dictionary time = dicService.getDicByDicCode("EXAM_TIME", tranType);
        if(time == null){
            return "";
        }
        String timeStr = time.getDictionaryName();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(timeStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int fate = DateTimeUtil.getDayDiffer(new Date(), date);
            if(fate <= 0){
                return "";
            }
            return String.format("距离%s%d月%d日考试：%d天", tranType, calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.DAY_OF_MONTH), fate);
        } catch (ParseException e) {
            return  "";
        }
    }


    public String getAnswer(String studentId, String bankId, String excerId, boolean isErrorBook){
        return answerMapper.getAnswer(studentId, bankId, excerId, isErrorBook ? "1" : "0");
    }

    public int getDoNum(String studentId, String bankId){
        Integer doNum = answerMapper.getDoNum(studentId, bankId);
        if(doNum == null){
            return 0;
        }
        return doNum;
    }

    public int getRight(String studentId, String bankId){
        Integer doNum = answerMapper.getRight(studentId, bankId);
        if(doNum == null){
            return 0;
        }
        return doNum;
    }

    public Map getAswers(String studentId, String bankId, int exerciseNum){
        Map<String, Object> result = new HashMap<>();
        List<StudentAnswer> aswers = answerMapper.getAswers(studentId, bankId);
        int right = 0;
        List info = new ArrayList();
        for(int i=0; i<exerciseNum; i++){
            int orderNo = i+1;
            int state = -1;
            for(StudentAnswer answer : aswers){
                if(answer.getOrderNo() == orderNo ){
                    if("1".equals(answer.getIsRight())){
                        right ++;
                    }
                    state = Integer.valueOf(answer.getIsRight());
                    break;
                }
            }
            Map infoMap =  new HashMap();
            infoMap.put("id", i+1);
            infoMap.put("state", state);
            info.add(infoMap);
        }
        result.put("right", right);
        result.put("error", aswers.size() - right);
        result.put("notDo", exerciseNum - aswers.size());
        String title = "正确:"+right + "   错误:"+(aswers.size() - right);
        result.put("title",  exerciseNum - aswers.size() == 0 ? title: title+ "   未答:" +(exerciseNum - aswers.size()));
        result.put("info", info);
        return result;
    }

    /**
     * 获取错题本中答对的记录
     * @param studentId
     * @return
     */
    public List<String> getRightExercise(String studentId){
        return answerMapper.getRightExercise(studentId);
    }

   public void restExercise(String studentId, String bankId, boolean isErrorBook){
       answerMapper.restExercise(studentId, bankId, isErrorBook ? "1" : "0");
   }


}
