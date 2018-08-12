package com.page.functions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestData {
	
	@Test
	public void getData() throws Throwable
	{
	
		String autodate="20-04-2016 10:19";
		
		SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date date = new Date();
		System.out.println(DtFormat.format(date).toString());
		
		

	}

}
