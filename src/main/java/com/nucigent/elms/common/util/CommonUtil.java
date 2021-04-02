package com.nucigent.elms.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;



@Component
public class CommonUtil {
	
	
	
	@Autowired
	Environment environment;
	
	public String fileUploadBasePath(){
		String fileUploadBasePath = "";
		//System.setProperty("catalina.home", "G:/AMS_New/apache-tomcat-8.0.39");
		fileUploadBasePath = System.getProperty("catalina.home");
		return fileUploadBasePath;
	}
	
	public String getCurrentUserName(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		
		return currentPrincipalName;
		
	}
	
	public static String maskString(String strText, int start, int end, char maskChar) 
	       {
	        
	        if(strText == null || strText.equals(""))
	            return "";
	        
	        if(start < 0)
	            start = 0;
	        
	        if( end > strText.length() )
	            end = strText.length();
	                       
	        
	        int maskLength = end - start;
	        
	        if(maskLength == 0)
	            return strText;
	        
	        StringBuilder sbMaskString = new StringBuilder(maskLength);
	        
	        for(int i = 0; i < maskLength; i++){
	            sbMaskString.append(maskChar);
	        }
	        
	        return strText.substring(0, start) 
	            + sbMaskString.toString() 
	            + strText.substring(start + maskLength);
	    }
	
	

}
