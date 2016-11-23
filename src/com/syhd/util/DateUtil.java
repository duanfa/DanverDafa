package com.syhd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

public class DateUtil {
	public final static SimpleDateFormat day_sdf = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat day_simple_sdf = new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat time_stamp_sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getTimeFormat(Date date) {
		return time_sdf.format(date);
	}

	public static String getDayFormat(Date date) {
		return day_sdf.format(date);
	}

	public static String getTimeStampFormat(Date date) {
		return time_stamp_sdf.format(date);
	}

	/**
	 * 获取指定日期(dateStr)之前的beforeDays天的日期
	 * 
	 * @param dateStr
	 * @param beforeDays
	 * @return
	 */
	public static String getSpecialDayBefore(String dateStr, int beforeDays) {
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day - beforeDays);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return dayBefore;
	}

	/**
	 * 获取指定日期(dateStr)之前的beforeDays天的日期列表
	 * 
	 * @param dateStr
	 * @param beforeDays
	 * @return
	 */
	public static List<String> getSpecialDayListBefore(String dateStr, int beforeDays) {
		List<String> list = new ArrayList<String>();

		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (int i = beforeDays; i > 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day = cal.get(Calendar.DATE);
			cal.set(Calendar.DATE, day - i);
			String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			list.add(dayBefore);
		}
		return list;
	}

	/**
	 * 获取指定日期(dateStr)之前的beforeDays天的日期列表
	 * 
	 * @param dateStr
	 * @param beforeDays
	 * @return
	 */
	public static List<String> getSpecialDayListBeforeIncludeSpecialDay(String dateStr, int beforeDays) {
		List<String> list = new ArrayList<String>();

		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (int i = beforeDays; i >= 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day = cal.get(Calendar.DATE);
			cal.set(Calendar.DATE, day - i);
			String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			list.add(dayBefore);
		}
		return list;
	}

	/**
	 * 获取指定日期(dateStr)之前的beforeMonths月的日期
	 * 
	 * @param dateStr
	 * @param beforeDays
	 * @return
	 */
	public static String getSpecialMonthBefore(String dateStr, int beforeMonths) {
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.MONTH, beforeMonths);
		String monthBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return monthBefore;
	}

	/**
	 * 两个日期相差天数(不跨年)
	 * 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	/**
	 * 两个日期相差天数(跨年)
	 * 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwoDate(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		Calendar bCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		bCalendar.setTime(oDate);
		int days = 0;
		while (aCalendar.before(bCalendar)) {
			days++;
			aCalendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		return days;
	}

	/**
	 * 两个日期相差月数(跨年)
	 * 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int monthsOfTwoDate(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		Calendar bCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		bCalendar.setTime(oDate);
		int months = 0;
		while (aCalendar.before(bCalendar)) {
			months++;
			aCalendar.add(Calendar.MONTH, 1);
		}
		return months;
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		// String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四",
		// "星期五","星期六" };
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysCode[intWeek];
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekNameOfDate(Date date) {
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		// String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	/**
	 * 获得指定日期前/后周日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSpecialDay(Date date, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DATE, days);// -7:前一周　7:后一周
		return calendar.getTime();

	}

	/**
	 * 获得周一的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonday(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return calendar.getTime();

	}

	/**
	 * 获得周三的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWednesday(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

		return calendar.getTime();

	}

	/**
	 * 获得周五的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFriday(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

		return calendar.getTime();
	}

	/**
	 * 获得周日的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSunday(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		return cal.getTime();
	}

	/**
	 * 当前日期前几天或者后几天的日期
	 * 
	 * @param n
	 * @return
	 */
	public static Date afterNDay(int n) {

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());

		cal.add(Calendar.DATE, n);

		return cal.getTime();

	}

	public static Date getMiddleTime(Date start, Date end) {
		return new Date((start.getTime() + end.getTime()) / 2);
	}

	/**
	 * 判断两个日期是否在同一周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setFirstDayOfWeek(Calendar.MONDAY);
		cal2.setFirstDayOfWeek(Calendar.MONDAY);
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	// 取得日期是某年的第几周
	public static int getWeekOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}

	// 取得某个月有多少天
	public static int getDaysOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		int days_of_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days_of_month;
	}

	// 取得两个日期之间的相差多少天
	public static long getDaysBetween(Date date0, Date date1) {
		long daysBetween = (date0.getTime() - date1.getTime() + 1000000) / 86400000;// 86400000=3600*24*1000
																					// 用立即数，减少乘法计算的开销
		return daysBetween;
	}

	public static void main(String[] args) throws ParseException {
		/*
		 * List<String> list = DateUtil.getSpecialDayListBefore(
		 * DateUtil.day_sdf.format(new Date()), 7); for (String day : list) {
		 * System.out.println(day); }
		 * System.out.println(DateUtil.day_sdf.parse(list.get(0)).getClass());
		 */
		String weekday = getWeekOfDate(getSunday(new Date()));
		System.out.println("weekday : " + (double) 1 / 2);
		System.out.println("sunday : " + getSunday(new Date()));
		System.out.println("monday : " + getMonday(new Date()));
		System.out.println("monday : " + getSpecialDay(getMonday(new Date()), 7));
	}
}
