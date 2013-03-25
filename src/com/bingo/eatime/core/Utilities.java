package com.bingo.eatime.core;

import java.util.Calendar;
import java.util.Date;

public class Utilities {

	public static String getKeyFromName(String name) {
		String keyName = name.trim().replace(' ', '-').toLowerCase();

		return keyName;
	}

	public static Date getStartEndDate(Date date, boolean start) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (start) {
			calendar.set(Calendar.HOUR_OF_DAY,
					calendar.getMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND,
					calendar.getMinimum(Calendar.MILLISECOND));

			return calendar.getTime();
		} else {
			calendar.set(Calendar.HOUR_OF_DAY,
					calendar.getMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND,
					calendar.getMaximum(Calendar.MILLISECOND));

			return calendar.getTime();
		}
	}

}
