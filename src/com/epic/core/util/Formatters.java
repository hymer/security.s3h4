package com.epic.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工具类
 * 
 * @author hymer
 * 
 */
public class Formatters {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			DEFAULT_DATE_FORMAT);

	public static final String formatDate(Date date) {
		return SDF.format(date);
	}

	public static final String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date parseDate(String dateString) {
		try {
			return SDF.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final Date parseDate(String dateString, String format)
			throws ParseException {
		return new SimpleDateFormat(format).parse(dateString);
	}

	public static final String formatNumber(Number src) {
		return null;
	}

	public static final String formatNumber(Number src, String format) {
		return null;
	}

}
