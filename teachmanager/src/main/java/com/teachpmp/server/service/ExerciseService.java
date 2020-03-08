package com.teachpmp.server.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.teachpmp.server.dao.ExerciseMapper;
import com.teachpmp.server.entity.Dictionary;
import com.teachpmp.server.entity.Exercise;
import com.teachpmp.server.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExerciseService extends AbstractBaseService<Exercise>{

    @Autowired
    DicService dicService;

    private final ExerciseMapper exerciseMapper;
    @Autowired
    public ExerciseService(ExerciseMapper exerciseMapper) {
        super(exerciseMapper);
        this.exerciseMapper = exerciseMapper;
    }

    public Exercise getExercise(Exercise exercise) {
        return exerciseMapper.getExercise(exercise);
    }

    public int deleteByBankId(String bankId) {
        return exerciseMapper.deleteByBankId(bankId);
    }

    public void multiDelete(String[] excerIds){
        exerciseMapper.multiDelete(excerIds);
    }

    public Integer countExercise(String bankId){
        return exerciseMapper.countExercise(bankId);
    }

    public String  importExl(MultipartFile file, String bankId, String userName) throws Exception{
        int success = 0;
        List<Integer> fail = new ArrayList<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<Exercise> exercises = ExcelImportUtil.importExcel(file.getInputStream(), Exercise.class, params);
        List<Dictionary> domains = dicService.getDictionary("DOMAIN");
        List<Dictionary> process = dicService.getDictionary("PROCESS");
        int row = 2;
        for(Exercise exercise : exercises){
            exercise.setBankId(bankId);
            if(exercise.getOrderNo() == null || exercise.getOrderNo() <= 0){
                fail.add(row);
                continue;
            }
            if(StringUtils.isBlank(exercise.getContent())){
                fail.add(row);
                continue;
            }
            exercise.setOwnDomain(convertName(domains, exercise.getOwnDomain()));
            exercise.setOwnProcess(convertName(process, exercise.getOwnProcess()));
            Exercise existExercise = getExercise(exercise);
            if(existExercise == null){
                exercise.setExcerId(CommonUtils.getUUID());
                exercise.setCreateTime(new Date());
                exercise.setCreateUser(userName);
                insert(exercise);
            }else{
                exercise.setExcerId(existExercise.getExcerId());
                exercise.setUpdateTime(new Date());
                exercise.setUpdateUser(userName);
                updateByPrimaryKey(exercise);
            }
            success++;
            row ++;
        }
        String msg = "成功导入" + success + "行";
        if(fail.size() > 0){
            msg += "；导入失败行号：" + fail.toString();
        }
        return msg;
    }


    public HSSFWorkbook export(Exercise model){
        List<Dictionary> domains = dicService.getDictionary("DOMAIN");
        List<Dictionary> process = dicService.getDictionary("PROCESS");
        HSSFWorkbook workbook = createTemplate();
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<Exercise> result = exerciseMapper.page(model);
        int rowIndex = 1;
        for(Exercise exercise : result){
            HSSFRow row = sheet.createRow(rowIndex ++);
            int i = 0;
            HSSFCell cell = row.createCell(i++);
            cell.setCellValue(exercise.getOrderNo() == null ? "" : String.valueOf(exercise.getOrderNo()));
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getContent());
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getSelectionA());
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getSelectionB());
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getSelectionC());
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getSelectionD());
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getSelectionCorrect());
            cell = row.createCell(i++);
            cell.setCellValue(exercise.getAnswer());
            cell = row.createCell(i++);
            cell.setCellValue(convertCode(domains, exercise.getOwnDomain()));
            cell = row.createCell(i++);
            cell.setCellValue(convertCode(process, exercise.getOwnProcess()));
        }
        return workbook;
    }

    private String convertCode(List<Dictionary> dictionarys, String code){
        for(Dictionary dictionary : dictionarys){
            if(dictionary.getDictionaryCode().equals(code)){
                return dictionary.getDictionaryName();
            }
        }
        return code;
    }

    private String convertName(List<Dictionary> dictionarys, String name){
        for(Dictionary dictionary : dictionarys){
            if(dictionary.getDictionaryName().equals(name)){
                return dictionary.getDictionaryCode();
            }
        }
        return name;
    }


    public HSSFWorkbook createTemplate(){
        List<Dictionary> domains = dicService.getDictionary("DOMAIN");
        List<Dictionary> process = dicService.getDictionary("PROCESS");
        return createTemplate(new List[]{domains, process});
    }

    private HSSFWorkbook createTemplate(List[] select){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 256*10);
        sheet.setColumnWidth(1, 256*40);
        sheet.setColumnWidth(2, 256*15);
        sheet.setColumnWidth(3, 256*15);
        sheet.setColumnWidth(4, 256*15);
        sheet.setColumnWidth(5, 256*15);
        sheet.setColumnWidth(7, 256*25);
        HSSFFont font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        HSSFRow row = sheet.createRow(0);
        String heads = "习题序号,习题内容,A选项,B选项,C选项,D选项,正确答案,解析,所属领域,所属过程";
        int i = 0;
        for(String head : heads.split(",")){
            HSSFCell cell = row.createCell(i++);
            cell.setCellValue(head);
            cell.setCellStyle(cellStyle);
        }
        initSelect(sheet, select[0], 8);
        initSelect(sheet, select[1], 9);
        return workbook;
    }


    private void initSelect(HSSFSheet sheet, List<Dictionary> dictionaries, int col){

       String[] optionArr = new String[dictionaries.size()];
       int i = 0;
       for(Dictionary dictionary : dictionaries){
           optionArr[i++] = dictionary.getDictionaryName();
       }
        CellRangeAddressList regions1 = new CellRangeAddressList(0, 10000, col, col);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(optionArr);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions1, constraint);
        sheet.addValidationData(dataValidation);
    }
}
