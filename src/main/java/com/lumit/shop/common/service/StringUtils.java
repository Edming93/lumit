package com.lumit.shop.common.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static String setDateFormat(String datetime) {
    	
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
    
    public static String getData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		// 현재날짜를받아옴
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
        // "-" 를 운영체제에 맞게 / 또는 \\ 으로 변경한다.
	}
}
