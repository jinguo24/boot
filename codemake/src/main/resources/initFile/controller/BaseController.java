package com.simple.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
	 @InitBinder
	    public void InitBinder(WebDataBinder dataBinder) {  
	        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {  
	            public void setAsText(String value) {  
	                try {  
	                    setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));  
	                } catch(ParseException e) {  
	                    try {
							setValue(new SimpleDateFormat("yyyy-MM-dd ").parse(value));
						} catch (ParseException e1) {
							 setValue(null);  
						}  
	                }  
	            }  
	      
	            public String getAsText() {  
	                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());  
	            }          
	      
	        });  
	    }
}
