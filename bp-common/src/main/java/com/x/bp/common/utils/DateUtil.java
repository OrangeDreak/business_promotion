package com.x.bp.common.utils;

import com.x.bp.common.context.ApiContext;
import com.x.bp.common.enums.LanguageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 时间工具
 */
@Slf4j
public class DateUtil {
    private static final TimeZone defaultTimeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT"));

    public static final FastDateFormat YMD_CN_FORMAT = FastDateFormat.getInstance("yyyyMMdd", TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT")));

    public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", defaultTimeZone);
    public static final FastDateFormat DATE_DOT_FORMAT = FastDateFormat.getInstance("yyyy.MM.dd HH:mm:ss", defaultTimeZone);
    public static final FastDateFormat YMD_DOT_FORMAT = FastDateFormat.getInstance("yyyy.MM.dd", defaultTimeZone);

    public static FastDateFormat getDATE_FORMAT(){
        String lan = ApiContext.getContext().getLange();
        TimeZone timeZone;
        if (LanguageTypeEnum.EN.getEnName().equals(lan)) {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("IET"));
        } else {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT"));
        }
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", timeZone);
    }

    public static FastDateFormat getDATE_DOT_FORMAT(){
        String lan = ApiContext.getContext().getLange();
        TimeZone timeZone;
        if (LanguageTypeEnum.EN.getEnName().equals(lan)) {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("IET"));
        } else {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT"));
        }
        return FastDateFormat.getInstance("yyyy.MM.dd HH:mm:ss", timeZone);
    }

    public static FastDateFormat getYMD_DOT_FORMAT(){
        String lan = ApiContext.getContext().getLange();
        TimeZone timeZone;
        if (LanguageTypeEnum.EN.getEnName().equals(lan)) {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("IET"));
        } else {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT"));
        }
        return FastDateFormat.getInstance("yyyy.MM.dd", timeZone);
    }


    public static Date yesterdayStart() {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date yesterdayEnd() {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date todayStart() {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date someDayStart(Date someDay) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(someDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date someDayEnd(Date someDay) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(someDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date sameTimeYesterday(Date someDay) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(someDay);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date sameTimeTomorrow(Date someDay) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(someDay);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date someDayHourStart(Date someDay) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(someDay);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date todayHourStart() {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date todayEnd() {
        Calendar calendar = Calendar.getInstance(defaultTimeZone);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date todayEndTimeZone() {
        String lan = ApiContext.getContext().getLange();
        TimeZone timeZone;
        if (LanguageTypeEnum.EN.getEnName().equals(lan)) {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("IET"));
        } else {
            timeZone = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT"));
        }
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date weekStart() {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date monthStart(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date monthStart() {
        return monthStart(new Date());
    }

    public static Date monthMiddle(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        return cal.getTime();
    }

    public static Date monthMiddle() {
        return monthMiddle(new Date());
    }

    public static Date monthEnd(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date monthEnd() {
        return monthEnd(new Date());
    }

    public static Date yearStart(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        return cal.getTime();
    }

    public static Date yearStart() {
        return yearStart(new Date());
    }

    public static Date quarterStart(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (month < 3) {
            cal.set(Calendar.MONTH, 0);
        } else if (month < 6) {
            cal.set(Calendar.MONTH, 3);
        } else if (month < 9) {
            cal.set(Calendar.MONTH, 6);
        } else {
            cal.set(Calendar.MONTH, 9);
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    public static Date quarterStart() {
        return quarterStart(new Date());
    }

    /**
     * 查看当月多少天
     */
    public static int getMonthDays(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定月份已经过去的日期
     * 如 今天是08.10
     * 指定月份是7月  则返回07.01 - 07.31 31个日期
     * 指定月份是8月  则返回08.01 - 08.10 10个日期
     * 指定月份是9月  则返回0个日期
     *
     * @param date 指定月份
     */
    public static List<Date> monthPastDays(Date date) {
        List<Date> pastDays = new ArrayList<>();
        Date monthStart = monthStart(date);
        Date now = new Date();
        for (int i = 0; i < getMonthDays(date); i++) {
            Date day = DateUtils.addDays(monthStart, i);
            if (day.after(now)) {
                break;
            }
            pastDays.add(day);
        }
        return pastDays;
    }

    public static Date addMonth(Date date, Integer add) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.add(Calendar.MONTH, add);
        return cal.getTime();
    }

    public static Date addDay(Date date, Integer add) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, add);
        return cal.getTime();
    }

    public static Date addHour(Date date, Integer add) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, add);
        return cal.getTime();
    }

    public static Integer getHour(Date date) {
        Calendar cal = Calendar.getInstance(defaultTimeZone);
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate localStartDate = startDate.toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
        return ChronoUnit.DAYS.between(localStartDate, localEndDate);
    }

    public static long calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
