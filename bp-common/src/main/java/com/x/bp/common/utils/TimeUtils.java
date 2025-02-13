package com.x.bp.common.utils;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @description：时间工具类
 * @author： SuoLun
 * @create： 2024/1/12 16:47
 */
@Slf4j
public class TimeUtils {

    public static Long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取昨天的开始结束时间
     * @return
     */
    public static Map<String,String> getYesterdayTime() {
        Long startTime = getBeginDayOfYesterday();
        Long endTime = getEndDayOfYesterDay();
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault()));
        String endTimeStr = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault()));
        Map map = new HashMap(2);
        map.put("startDate", startTimeStr);
        map.put("endDate", endTimeStr);
        return map;
    }


    public static Long getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTimeInMillis();
    }

    public static Long getEndDayOfYesterDay() {

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.add(Calendar.DAY_OF_MONTH, -1);


        return cal.getTimeInMillis();
    }

    /**
     * @return
     * @description 获取以秒为单位的下一个小时时间戳
     */
    public static long getNextDayTimestampInSecond() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY, +1);
        return cal.getTime().getTime() / 1000;
    }

    /**
     * @return
     * @description 获取以秒为单位的上一个小时时间戳
     */
    public static long getPreDayTimestampInSecond() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY, -1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime().getTime() / 1000;
    }

    /**
     * @param timestampInSecond 时间戳（秒）
     * @param dateFormatStr     转化的时间格式
     * @return
     * @description 时间戳转化为指定格式的日期格式
     */
    public static String convertTimestampToDate(long timestampInSecond, String dateFormatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);

        return sdf.format(new Date(timestampInSecond * 1000));
    }

    public static Date getDateByLong(Long longTime) {
        if (!Validator.greaterZero(longTime)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(longTime * 1000);
        try {
            Date date = format.parse(d);
            return date;
        } catch (Exception e) {
            log.error("时间转换异常，longTime：{}", longTime, e);
        }
        return null;
    }

    public static Date YMDHMSTODate(String time) {
        if (Validator.isNullOrEmpty(time)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date YMDTODate(String time) {
        if (Validator.isNullOrEmpty(time)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (Validator.isNullOrEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(date);
            //Date类型转String类型
            return time;
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToString(Date date, String pattern) {
        if (Validator.isNullOrEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String time = simpleDateFormat.format(date);
            //Date类型转String类型
            return time;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Date转LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        try {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            return instant.atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param datetime      日期格式时间
     * @param dateFormatStr 日期格式
     * @return
     * @description 日期格式转化为时间戳（秒）
     */
    public static long convertDateToTimestampInSecond(String datetime, String dateFormatStr) {
        if (null == datetime || "".equals(datetime)) {
            return 0L;
        }
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            return 0L;
        }

        return date.getTime() / 1000;
    }

    public static boolean isValidPdPayTime(Date pdPayTime) {
        Date limitDate = new Date(1672502400000L);
        return null != pdPayTime && pdPayTime.after(limitDate);
    }

    /**
     * 获取指定日期所在月的开始时间
     *
     * @return
     */
    public static LocalDateTime getMonthBegin(LocalDate date) {
        return getStartOrEndDayOfMonth(date, true);
    }

    /**
     * 获取指定日期所在月的结束时间
     *
     * @return
     */
    public static LocalDateTime getMonthEnd(LocalDate date) {
        return getStartOrEndDayOfMonth(date, false);
    }

    /**
     * @param time yyyyMM
     * @return LocalDate
     */
    public static LocalDate yMToLocalDate(String time) {
        LocalDate localDate = LocalDate.parse(time + "-01");
        return localDate;
    }

    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static LocalDateTime getMonthBegin() {
        return getStartOrEndDayOfMonth(LocalDate.now(), true);
    }

    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static LocalDateTime getMonthEnd() {
        return getStartOrEndDayOfMonth(LocalDate.now(), false);
    }

    /**
     * 获取某一天所在月的开始时间
     *
     * @param resDate
     * @param isFirst
     * @return
     */
    public static LocalDateTime getStartOrEndDayOfMonth(LocalDate resDate, boolean isFirst) {
        LocalTime localTime = null;
        Month month = resDate.getMonth();
        int length = month.length(resDate.isLeapYear());
        if (isFirst) {
            resDate = LocalDate.of(resDate.getYear(), month, 1);
            localTime = LocalTime.MIN;
        } else {
            resDate = LocalDate.of(resDate.getYear(), month, length);
            localTime = LocalTime.MAX;
        }
        return LocalDateTime.of(resDate, localTime);
    }

    public static DateTime localDateTimeTODateTime(LocalDateTime time) {
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        return cn.hutool.core.date.DateUtil.dateNew(date);
    }

    public static Date localDateTimeTODate(LocalDateTime time) {
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static Date nowTimeAddYear(Date date, Integer year) {
        // 使用Calendar类增加一年
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        // 获取修改后的时间
        return calendar.getTime();
    }
    public static Date nowTimeAddDay(Date nowTime ,Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowTime);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static LocalDateTime getMinusAgo(Integer minutesAgo) {
        return LocalDateTime.now().minusMinutes(minutesAgo);
    }

    /**
     * 按照当天日期+固定常量 md5生成密码
     * @return
     */
    public static String generatePassword(){
        String password = cn.hutool.core.date.DateUtil.format(new Date(), "YYYY-MM-dd") + "fans2024";
        return DigestUtils.md5Hex(password);
    }

}
