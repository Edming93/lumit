package com.lumit.shop.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static String setDateFormat(String datetime) {
    	SimpleDateFormat  inputFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    	SimpleDateFormat  outputFormatter = new SimpleDateFormat("YYYY-MM-dd");

    	Date date = new Date();
    	
    	try {
    		date = inputFormatter.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return outputFormatter.format(date);

    }
}
