package com.simple.common.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;

public class ExcelUtil {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	
	public static Workbook getWorkBook(InputStream is,String suffix) {
    	if (OFFICE_EXCEL_2003_POSTFIX.equals(suffix)) {
    		try {
    			if ( null != is) {
    				return new HSSFWorkbook(is);
    			}else {
    				return new HSSFWorkbook(); 
    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return null;
        } else if (OFFICE_EXCEL_2010_POSTFIX.equals(suffix)) {
    		try {
    			if ( null != is ) {
    				return new XSSFWorkbook(is);
    			}else {
    				return new XSSFWorkbook();
    			}
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    		return null;
        }
    	return null;
    }
	
	public static void createFile(Workbook workbook,String filefolder,String fileName) {
    	try {
    		File file = new File(filefolder);
    		if (!file.exists()) {
    			file.mkdirs();
    		}
    		
    		File destionFile = new File(filefolder+"/"+fileName);
    		if (!destionFile.exists()) {
    			destionFile.createNewFile();
    		}
			FileOutputStream outputStream = new FileOutputStream(destionFile);
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void pushToResponse(Workbook workbook,HttpServletResponse response,String filename) {
        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("application/x-msdownload");
            //String inlineType = "attachment"; // 是否内联附件
            response.setHeader("Content-Disposition","attachment;filename=\"" + filename + "\"");
            OutputStream out=response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	public static void pushJsonToResponse(ServletResponse response, Object data) {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			String dataStr = JSON.toJSON(data).toString();
			out.println(dataStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
