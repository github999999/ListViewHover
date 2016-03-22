package xzh.com.listviewhover.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期时间工具类
 */
public class DateUtil extends DateUtils {

    public enum DifferenceMode {
        Second,
        Minute,
        Hour,
        Day
    }

    public static long calculateDifferentSecond(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Second);
    }

    public static long calculateDifferentMinute(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Minute);
    }

    public static long calculateDifferentHour(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Hour);
    }

    public static long calculateDifferentDay(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Day);
    }

    public static long calculateDifferentSecond(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Second);
    }

    public static long calculateDifferentMinute(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Minute);
    }

    public static long calculateDifferentHour(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Hour);
    }

    public static long calculateDifferentDay(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Day);
    }

    public static long calculateDifference(long startTimeMillis, long endTimeMillis, DifferenceMode mode) {
        return calculateDifference(new Date(startTimeMillis), new Date(endTimeMillis), mode);
    }

    public static long calculateDifference(Date startDate, Date endDate, DifferenceMode mode) {
        long[] different = calculateDifference(startDate, endDate);
        if (mode.equals(DifferenceMode.Minute)) {
            return different[2];
        } else if (mode.equals(DifferenceMode.Hour)) {
            return different[1];
        } else if (mode.equals(DifferenceMode.Day)) {
            return different[0];
        } else {
            return different[3];
        }
    }

    public static long[] calculateDifference(Date startDate, Date endDate) {
        return calculateDifference(endDate.getTime() - startDate.getTime());
    }

    public static long[] calculateDifference(long differentMilliSeconds) {
        long secondsInMilli = 1000;//1s==1000ms
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = differentMilliSeconds / daysInMilli;
        differentMilliSeconds = differentMilliSeconds % daysInMilli;
        long elapsedHours = differentMilliSeconds / hoursInMilli;
        differentMilliSeconds = differentMilliSeconds % hoursInMilli;
        long elapsedMinutes = differentMilliSeconds / minutesInMilli;
        differentMilliSeconds = differentMilliSeconds % minutesInMilli;
        long elapsedSeconds = differentMilliSeconds / secondsInMilli;
        return new long[]{elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds};
    }

    public static int calculateDaysInMonth(int month) {
        return calculateDaysInMonth(0, month);
    }

    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    public static boolean isSameDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date);
        return (nowCalendar.get(Calendar.ERA) == newCalendar.get(Calendar.ERA) &&
                nowCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR) &&
                nowCalendar.get(Calendar.DAY_OF_YEAR) == newCalendar.get(Calendar.DAY_OF_YEAR));
    }

    public static Date parseDate(String dateStr, String dataFormat) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
            Date date = dateFormat.parse(dateStr);
            return new Date(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static int getYear(){
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        return year;
    }

    public static int getMonth(){
        Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH);
        return month+1;
    }

    public static int getDay(){
        Calendar c = Calendar.getInstance();
        int day=c.get(Calendar.DATE);
        return day;
    }

  public static String getToday(){
      Date now = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String today=dateFormat.format(now);
      return today;
  }

}
