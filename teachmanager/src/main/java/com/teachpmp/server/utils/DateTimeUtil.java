package com.teachpmp.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

/**
 * @author alvis
 */
public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
    public static final String STANDER_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDER_SHORT_FORMAT = "yyyy-MM-dd";

    public static Date addDuration(Date date, Duration duration) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.SECOND, (int) duration.getSeconds());
        return ca.getTime();
    }

    public static String dateFormat(Date date) {
        if (null == date) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(STANDER_FORMAT);
        return dateFormat.format(date);
    }

    public static String dateShortFormat(Date date) {
        if (null == date) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(STANDER_SHORT_FORMAT);
        return dateFormat.format(date);
    }

    public static Date parse(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Date getMonthStartDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String dateStr = formatter.format(cale.getTime());
        return parse(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getMonthEndDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String dateStr = formatter.format(cale.getTime());
        return parse(dateStr, STANDER_FORMAT);
    }


    public static List<String> MothStartToNowFormat() {
        Date startTime = getMonthStartDay();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        int mothDayCount = nowCalendar.get(Calendar.DAY_OF_MONTH);
        List<String> mothDays = new ArrayList<>(mothDayCount);
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        mothDays.add(formatter.format(startTime));
        for (int i = 0; i < mothDayCount - 1; i++) {
            startCalendar.add(Calendar.DATE, 1);
            Date end_date = startCalendar.getTime();
            mothDays.add(formatter.format(end_date));
        }
        return mothDays;
    }


    public static List<String> MothDay() {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(getMonthEndDay());
        int endMothDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        List<String> list = new ArrayList<>(endMothDay);
        for (int i = 1; i <= endMothDay; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public static int getDayDiffer(Date startDate, Date endDate) throws ParseException {
        //判断是否跨年
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String startYear = yearFormat.format(startDate);
        String endYear = yearFormat.format(endDate);
        if (startYear.equals(endYear)) {
            /*  使用Calendar跨年的情况会出现问题    */
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int startDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(endDate);
            int endDay = calendar.get(Calendar.DAY_OF_YEAR);
            return endDay - startDay;
        } else {
            /*  跨年不会出现问题，需要注意不满24小时情况（2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0）  */
            //  只格式化日期，消除不满24小时影响
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
            return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
        }
    }


}
