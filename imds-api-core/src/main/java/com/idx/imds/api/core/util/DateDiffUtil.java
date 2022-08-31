package com.idx.imds.api.core.util;

import java.time.Duration;
import java.util.Date;

public class DateDiffUtil {

	public static synchronized Long getDateDiffMilliSecond(Date firstDate, Date lastDate) {
		Duration duration = Duration.between(firstDate.toInstant(), lastDate.toInstant());
		duration = duration.plusHours(1L);
		Long diffMilis = duration.toMillis();
		long diffHours = duration.toHours();
		long diffDay = duration.toDays();

		System.out.println("MILIS:"+diffMilis);
		System.out.println("HOURS:"+diffHours);
		System.out.println("DAYS:"+diffDay);
		return diffMilis;

	}

}
//