package com.jy.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JunYu
 * @date 2025/3/8
 * @Description:
 */
public class DateTimeUtils {


    private DateTimeUtils(){}

    /** MM-dd */
    public static final String MMDD_EN = "MM-dd";
    /** HH:mm:ss */
    public static final String HHMMSS_EN = "HH:mm:ss";
    /** yyyy */
    public static final String YYYY_EN = "yyyy";
    public static final String YYYYMMEN = "yyyyMM";
    /** yyyy-MM-dd */
    public static final String YYYYMM_EN = "yyyy-MM";
    /** yyyy-M-d */
    public static final String YYYYMD_EN = "yyyy-M-d";
    /** yyyy-MM-dd */
    public static final String YYYYMMDD_EN = "yyyy-MM-dd";
    /** yyyy-MM-dd HH */
    public static final String YYYYMMDDHH_EN = "yyyy-MM-dd HH";
    public static final String YYYYMMDDHH_ENCN = "yyyy-MM-dd HH时";
    /** yyyy-MM-dd HH:mm */
    public static final String YYYYMMDDHHMM_EN = "yyyy-MM-dd HH:mm";
    /** yyyy-MM-dd HH:mm:ss */
    public static final String YYYYMMDDHHMMSS_EN = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS_SSSSSS_XXX_EN = "yyyy-MM-dd HH:mm:ss.SSSSSS XXX";

    /** yyyyMMddHHmmss */
    public static final String YYYYMMDDHHMMSS_NOTCHAR_EN = "yyyyMMddHHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";
    /** MM月-dd日 */
    public static final String MMDD_CN = "MM月dd日";
    /** HH时mm分ss秒 */
    public static final String HHMMSS_CN = "HH时mm分ss秒";
    /** yyyy年MM月 */
    public static final String YYYYMM_CN = "yyyy年MM月";
    /** yyyy年M月d日 */
    public static final String YYYYMD_CN = "yyyy年M月d日";
    /** yyyy年MM月dd日 */
    public static final String YYYYMMDD_CN = "yyyy年MM月dd日";

    public static final String YYYYMMDD_HHMM_CN = "yyyy年MM月dd日 HH:mm";
    /** yyyy年MM月dd日HH时 */
    public static final String YYYYMMDDHH_CN = "yyyy年MM月dd日HH时";
    /** yyyy年MM月dd日HH时mm分 */
    public static final String YYYYMMDDHHMM_CN = "yyyy年MM月dd日 HH时mm分";
    /** yyyy年MM月dd日HH时mm分ss秒 */
    public static final String YYYYMMDDHHMMSS_CN = "yyyy年MM月dd日HH时mm分ss秒";

    /** yyyy.MM.dd日 */
    public static final String YYYY_MM_DD = "yyyy.MM.dd";


    /**
     * 根据日期格式获取当前时间
     * @param pattern 日期格式
     * @return
     */
    public static String getTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化日期为字符串
     *
     * @param date date
     * @param pattern 格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern){
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析字符串日期为Date
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return Date
     */
    public static Date parse(String dateStr, String pattern) {
        LocalDateTime localDateTime;
        try{
            localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        }catch (Exception e){
            localDateTime = LocalDateTime.of(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern)), LocalTime.MIN);
        }
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当天开始时间字符串
     * @param pattern 时间格式
     * @return
     */
    public static String getStartTimeStr(String pattern) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当天结束时间字符串
     * @param pattern 时间格式
     * @return
     */
    public static String getEndTimeStr(String pattern) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取某个日期的开始时间
     * @param date
     * @return
     */
    public static Date getDayStartTime(Date date) {
        LocalDate day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(LocalDateTime.of(day, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取某个日期的结束时间
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {
        LocalDate day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(LocalDateTime.of(day, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取给予日期的月份的第一天
     * @param date 日期
     * @return
     */
    public static Date getBeginDayOfMonth(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取给予日期的月份的最后一天
     * @param date
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(LocalDateTime.of(localDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取给予日期的年份的第一天
     * @param date 日期
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取给予日期的年份的最后一天
     * @param date
     * @return
     */
    public static Date getEndDayOfYear(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(LocalDateTime.of(localDate.with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 格式化时间显示格式   xx天xx小时
     * @param timeMillis 时间戳
     * @return
     */
    public static String formatTimeMills(long timeMillis){
        String timeMillsString = "";
        if (timeMillis != 0) {
            long day = timeMillis / (1000 * 60 * 60 * 24);
            long hour = (timeMillis / (1000 * 60 * 60) - day * 24);
            long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day > 0) {
                timeMillsString = day + "天" + hour + "小时" + min + "分";
            } else if (day == 0 && hour > 0) {
                timeMillsString = hour + "小时" + min + "分";
            } else {
                timeMillsString = min + "分" + s + "秒";
            }
        }
        return timeMillsString;
    }

    /**
     * 获取给予日期的n天后
     * @param date
     * @return
     */
    public static Date getDaysAfterDate(Date date, long days) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(LocalDateTime.of(localDate, LocalTime.MIN).plusDays(days).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取给予日期的n月前
     * @param date
     * @return
     */
    public static Date getMonthsBeforeDate(Date date, long months) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(LocalDateTime.of(localDate, LocalTime.MIN).minusMonths(months).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static int getDayDiffer(Date startDate, Date endDate) {
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (int) (endLocalDate.toEpochDay() - startLocalDate.toEpochDay());
    }

    /**
     * Description:获取最近几年的年份
     * @author xiaoYang
     * @date 2022/11/17 11:18
     * @param numYear 年份数
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getYearList(int numYear) {
        List<String> yearList = new ArrayList<>();
        Date currentDate = new Date();
        String currYear = format(currentDate, DateTimeUtils.YYYY_EN);
        if (StringUtils.isNotBlank(currYear)) {
            int currYear_int = Integer.parseInt(currYear);
            yearList.add(""+currYear_int);
            for (int i = 0;i < numYear-1 ;i++){
                currYear_int--;
                yearList.add(""+currYear_int);
            }
        }
        return yearList;
    }

}
