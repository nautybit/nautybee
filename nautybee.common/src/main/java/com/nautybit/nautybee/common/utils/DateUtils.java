package com.nautybit.nautybee.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by UFO on 17/01/20.
 */
public class DateUtils {
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String Y_M_D_CN = "yyyy年MM月dd日";
    public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";
    public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyyMMdd";
    public static final String YMDHM = "yyyyMMddHHmm";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String YMDHMSS = "yyyyMMddHHmmsssss";
    public static final String ymd = "yyyy/MM/dd";
    public static final String ymd_HM = "yyyy/MM/dd HH:mm";
    public static final String ymd_HMS = "yyyy/MM/dd HH:mm:ss";
    public static final String YYMMDD = "yyMMdd";
    public static final String HM = "HHmm";
    public static final String EE ="EE";
    public static final String HMS = "HH:mm:ss";
    public final static String DAY_FIRST_TIME = " 00:00:00";
    public final static String DAY_LAST_TIME = " 23:59:59";


    public  static String dateFormat(Date date,String format) {
        if (date == null || format == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     *
     *
     * @param date
     * @return
     */
    public static String smartFormat(Date date) {
        String dateStr = null;
        if (date == null) {
            dateStr = "";
        } else {
            try {
                dateStr = formatDate(date, Y_M_D_HMS);
                //时分秒
                if (dateStr.endsWith(" 00:00:00")) {
                    dateStr = dateStr.substring(0, 10);
                }
                //时分
                else if (dateStr.endsWith("00:00")) {
                    dateStr = dateStr.substring(0, 16);
                }
                //秒
                else if (dateStr.endsWith(":00")) {
                    dateStr = dateStr.substring(0, 16);
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
            }
        }
        return dateStr;
    }

    /**
     *
     *
     * @param text
     * @return
     */
    public static Date smartFormat(String text) {
        Date date = null;
        try {
            if (text == null || text.length() == 0) {
                date = null;
            } else if (text.length() == 10) {
                date = formatStringToDate(text, Y_M_D);
            } else if (text.length() == 13) {
                date = new Date(Long.parseLong(text));
            } else if (text.length() == 16) {
                date = formatStringToDate(text, Y_M_D_HM);
            } else if (text.length() == 19) {
                date = formatStringToDate(text, Y_M_D_HMS);
            } else {
                throw new IllegalArgumentException("日期长度不符合要求!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("日期转换失败!");
        }
        return date;
    }

    /**
     * 获取当前日期
     * @param format
     * @return
     * @throws Exception
     */
    public static String getNow(String format) throws Exception{
        return formatDate(new Date(), format);
    }

    /**
     * 格式化日期格式
     *
     * @param argDate
     * @param argFormat
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date argDate, String argFormat) {
        if (argDate == null) {
            throw new RuntimeException("参数[日期]不能为空!");
        }
        if (StringUtils.isEmpty(argFormat)) {
            argFormat = Y_M_D;
        }
        SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat);
        return sdfFrom.format(argDate).toString();
    }

    /**
     * 格式化日期格式
     *
     * @param argDate
     * @param argFormat
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date argDate, String argFormat,Locale locale) {
        if (argDate == null) {
            throw new RuntimeException("参数[日期]不能为空!");
        }
        if (StringUtils.isEmpty(argFormat)) {
            argFormat = Y_M_D;
        }
        SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat,locale);
        return sdfFrom.format(argDate).toString();
    }

    /**
     * 把字符串格式化成日期
     *
     * @param argDateStr
     * @param argFormat
     * @return
     */
    public static Date formatStringToDate(String argDateStr, String argFormat) throws Exception {
        if (argDateStr == null || argDateStr.trim().length() < 1) {
            throw new Exception("参数[日期]不能为空!");
        }
        String strFormat = argFormat;
        if (StringUtils.isEmpty(strFormat)) {
            strFormat = Y_M_D;
            if (argDateStr.length() > 16) {
                strFormat = Y_M_D_HMS;
            } else if (argDateStr.length() > 10) {
                strFormat = Y_M_D_HM;
            }
        }
        SimpleDateFormat sdfFormat = new SimpleDateFormat(strFormat);
        //严格模式
        sdfFormat.setLenient(false);
        try {
            return sdfFormat.parse(argDateStr);
        } catch (ParseException e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取参数时间n天之后(之前)的时间
     * @param date 参数时间
     * @param days  可以为负数
     * @return date
     */
    public static Date getSomeDayDate(Date date,int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(GregorianCalendar.DAY_OF_YEAR,days);
        return cal.getTime();
    }

    /**
     * 将Y_M_D格式的日期字符串加上一天
     * @param dateStr 参数时间
     * @return string
     */
    public static String addOneDay(String dateStr) throws Exception {
        return addDay(dateStr, 1);
    }
    public static String addDay(String dateStr, int day) throws Exception {
        if (dateStr == null) {
            return null;
        }
        Date date = formatStringToDate(dateStr,Y_M_D);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,day);
        return formatDate(cal.getTime(),Y_M_D);
    }

    /**
     *
     * @param date
     * @param MM
     * @return
     */
    public static Date addMM(Date date, int MM){
        return org.apache.commons.lang3.time.DateUtils.addMonths(date,MM);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws java.text.ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
