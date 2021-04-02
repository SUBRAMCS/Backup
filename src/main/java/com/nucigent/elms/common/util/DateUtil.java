package com.nucigent.elms.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static String getExpireTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		String expireDate = dateFormat.format(calendar.getTime());
		return expireDate;
	}

	public static Date getCurrentDate() {
		Date date = new Date();
		return date;
	}

	public static String getCurrentTs() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String currDate = dateFormat.format(calendar.getTime());
		return currDate;
	}

	public static boolean lockStatus(String expireTS) {
		Date today = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date expire;
		try {
			expire = dateFormat.parse(expireTS);
			if (today.before(expire)) {
				return true;
			}
		} catch (ParseException e) {
			logger.error("Unable to parse the expireTS");
		}

		return false;
	}

	public static Date parseDate(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("Unable to parse provided date");
		}
		return parsedDate;
	}
}
