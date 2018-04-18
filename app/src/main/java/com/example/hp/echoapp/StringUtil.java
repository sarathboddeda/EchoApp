package com.example.hp.echoapp;

import android.util.Log;

public class StringUtil {
	/**
     * Check if the given string is either null or blank
     */
    public static boolean isNullOrEmpty(String value)
    {

        Log.d("value","vvv "+value);
        if (value == null){
            return true;
        }else{
        	value = value.trim();
        	if (value.length() == 0 
        			|| "na".equalsIgnoreCase(value) 
        			|| "null".equalsIgnoreCase(value)
        			|| "-".equals(value) 
        			|| "_".equals(value)){
                return true;
        	}
        }

        return false;
    }
    
  
}
