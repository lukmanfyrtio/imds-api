package com.idx.imds.api.core.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.idx.imds.api.core.util.DateDiffUtil;


public class DateDiffUtilTest {

	
	@Test
	public void getDateDiff() throws Exception {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Date expiredDate = format.parse("2022-04-11");
		 Date currentDate = new Date();
		 currentDate.setMinutes(0);
		 currentDate.setHours(0);
		 currentDate.setSeconds(0);
		 System.out.println(currentDate.toString());
		 System.out.println(expiredDate.toString());
		 
		 Long milisecond = DateDiffUtil.getDateDiffMilliSecond(currentDate, expiredDate);
		 
		 System.out.println(milisecond);
		
	}
}
