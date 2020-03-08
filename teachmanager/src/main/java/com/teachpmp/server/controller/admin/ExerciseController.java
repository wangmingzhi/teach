package com.teachpmp.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.teachpmp.server.controller.BaseApiController;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.Exercise;
import com.teachpmp.server.entity.Role;
import com.teachpmp.server.entity.base.RestResponse;
import com.teachpmp.server.service.BankService;
import com.teachpmp.server.service.DicService;
import com.teachpmp.server.service.ExerciseService;
import com.teachpmp.server.service.RoleService;
import com.teachpmp.server.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

@RestController("ExerciseController")
@RequestMapping(value = "/api/admin/exercise")
public class ExerciseController extends BaseApiController{

    @Autowired
    ExerciseService exerciseService;
    @Autowired
    BankService bankService;
    @Autowired
    DicService dicService;

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo> pageList(@RequestBody Exercise model) {
        PageInfo<Exercise> page = exerciseService.page(model, model.getPageIndex(), model.getPageSize(), "order_no asc");
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<Exercise> select(@PathVariable String id) {
        Exercise exercise = exerciseService.selectByPrimaryKey(id);
        if(StringUtils.isNotBlank(exercise.getBankId())){
           BankInfo bankInfo = bankService.selectByPrimaryKey(exercise.getBankId());
           exercise.setTrainType(bankInfo.getTrainType());
           exercise.setExerciseType(bankInfo.getExerciseType());
           exercise.setBankName(bankInfo.getBankName());
        }
        return RestResponse.ok(exercise);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<Exercise> edit(@RequestBody Exercise exercise) {
        if (exercise.getExcerId() == null) {  //create
            Exercise existExercise = exerciseService.getExercise(exercise);
            if (null != existExercise) {
                return new RestResponse<>(2, "习题序号已存在");
            }
        }
        if (exercise.getExcerId() == null) {
            exercise.setExcerId(CommonUtils.getUUID());
            exercise.setCreateTime(new Date());
            exercise.setCreateUser(getCurrentUserName());
            exerciseService.insert(exercise);
            bankService.updateExerciseNum(exercise.getBankId(), exerciseService.countExercise(exercise.getBankId()));
        } else {
            Exercise oldExercise = exerciseService.selectByPrimaryKey(exercise.getExcerId());
            if(!(oldExercise.getOrderNo().equals(exercise.getOrderNo())&& oldExercise.getBankId().equals(exercise.getBankId()))){
                Exercise existExercise = exerciseService.getExercise(exercise);
                if (null != existExercise) {
                    return new RestResponse<>(2, "习题序号已存在");
                }
            }
            exercise.setUpdateTime(new Date());
            exercise.setUpdateUser(getCurrentUserName());
            exerciseService.updateByPrimaryKey(exercise);
        }
        return RestResponse.ok(exercise);
    }

    @RequestMapping(value = "/delete/{bankId}/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String bankId, @PathVariable String id) {
        exerciseService.deleteByPrimaryKey(id);
        bankService.updateExerciseNum(bankId, exerciseService.countExercise(bankId));
        return RestResponse.ok();
    }

    @RequestMapping(value = "/multiDelete/{bankId}", method = RequestMethod.POST)
    public RestResponse multiDelete(@PathVariable String bankId, @RequestBody String[] ids) {
        if(ids!= null && ids.length >0){
            exerciseService.multiDelete(ids);
            bankService.updateExerciseNum(bankId, exerciseService.countExercise(bankId));
        }
        return RestResponse.ok();
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public RestResponse importExl(@RequestParam("file") MultipartFile file, String bankId) throws Exception{
        String msg = exerciseService.importExl(file, bankId, getCurrentUserName());
        bankService.updateExerciseNum(bankId, exerciseService.countExercise(bankId));
        return new RestResponse<>(1, msg);
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestBody Exercise exercise) throws Exception{
        HSSFWorkbook wb= exerciseService.export(exercise);
        BankInfo info = bankService.selectByPrimaryKey(exercise.getBankId());
        String trainType = dicService.getDicByDicCode("TRAN_TYPE_CODE", info.getTrainType()).getDictionaryName();
        String exerciseType = dicService.getDicByDicCode("EXER_TYPE_CODE", info.getExerciseType()).getDictionaryName();
        String fileName = trainType+ "_" + exerciseType +  "_" + info.getBankName();
        OutputStream output = response.getOutputStream();
        setResponsHeader(request, response, fileName + ".xls");
        wb.write(output);
        output.flush();
        output.close();
    }

    @RequestMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HSSFWorkbook wb = exerciseService.createTemplate();
        OutputStream output = response.getOutputStream();
        setResponsHeader(request, response, "习题模板.xls");
        wb.write(output);
        output.flush();
        output.close();
    }

    private void setResponsHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        fileName = URLEncoder.encode(fileName, "utf-8");
        response.reset();
        response.setHeader("Access-Control-Expose-Headers", "filename");
        response.setHeader("filename", fileName);
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

}
