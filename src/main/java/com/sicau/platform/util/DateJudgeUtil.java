package com.sicau.platform.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liuyuehe
 * @description 判断日期（是否为当日、当月、当周）
 * @date 2019/8/13
 */
public class DateJudgeUtil {
    /**
     * 判断选择的日期是否是今天
     */
    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    /**
     * 判断选择的日期是否是本周
     */
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }

    /**
     * 判断选择的日期是否是本月
     */
    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        //参数时间
        String param = sdf.format(date);
        //当前时间
        String now = sdf.format(new Date());
        return param.equals(now);
    }
}
