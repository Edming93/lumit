package com.lumit.shop.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static String setDateFormat(String datetime) {
    	
    	System.out.println(datetime);
    	SimpleDateFormat  inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat  outputFormatter = new SimpleDateFormat("yyyy-MM-dd");

    	Date date = new Date();
    	
    	try {
    		date = inputFormatter.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return outputFormatter.format(date);

    }
}
