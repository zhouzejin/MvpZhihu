package com.sunny.mvpzhihu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {

    public static String formatDate(String date) {
        String dateFormat = null;
        try {
            dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    public static String getTime(long date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(date);
    }

    public static String getWeek(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        Calendar calendar = Calendar.getInstance(); // 获得一个日历
        calendar.set(year, month - 1, day); // 设置当前时间，月份是从0月开始计算
        int number = calendar.get(Calendar.DAY_OF_WEEK); // 星期表示1-7，是从星期日开始
        return getWeekDay(number);
    }

    private static String getWeekDay(int dayForWeek) {
        switch (dayForWeek) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

}
