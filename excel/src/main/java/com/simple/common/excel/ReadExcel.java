package com.simple.common.excel;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.simple.config.EnvPropertiesConfiger;


/**
 * @author Hongten
 * @created 2014-5-20
 */
public class ReadExcel {
    
	
	private static final String ERROR_FILE_DIR = "/temp/error/excel/";
	
    
    
    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws Exception 
     * @throws IOException
     */
    public static ResponseInfo readReturnFile(InputStream is,ObjectExcutor excutor,String suffix) throws Exception {
    	ValidateResult vr = validateWorkBook(is,excutor,suffix);
        if (vr.ispass) {
        	return new ResponseInfo(new ResponseStatus(true,"校验成功"), vr.getDatas());
        }else {
        	String filefolder = ERROR_FILE_DIR+System.currentTimeMillis();
        	String fileName = PrimaryKeyUtil.getUUID()+".xlsx";
        	ExcelUtil.createFile(vr.getWorkbook(),EnvPropertiesConfiger.getValue("uploadDir")+filefolder,fileName);
        	return new ResponseInfo(new ResponseStatus(false,"3","读取文件失败"), filefolder+"/"+fileName);
        }
    }
    
    public static ResponseInfo readReturnWorkBook(InputStream is,ObjectExcutor excutor,String suffix) throws Exception {
    	try {
	    	ValidateResult vr = validateWorkBook(is,excutor,suffix);
	        if (vr.ispass) {
	        	return new ResponseInfo(new ResponseStatus(true,"校验成功"), vr.getDatas());
	        }else {
	        	return new ResponseInfo(new ResponseStatus(false,"读取文件失败"), vr.getWorkbook());
	        }
    	}catch(Exception e ) {
    		throw e;
    	}
    }
    
    public static String getErrorLogs(Workbook workBook,String suffix,HttpServletResponse response) {
    	String filename = DateUtil.date2StringWhitNoSpiltSeconds(new Date())+"-error."+suffix;
		ExcelUtil.pushToResponse(workBook, response, filename);
		return null;
    }
    
    private static class ValidateResult {
    	public ValidateResult(Workbook workbook, boolean ispass, List datas) {
			this.workbook = workbook;
			this.ispass = ispass;
			this.datas = datas;
		}
		private Workbook workbook;
    	private boolean ispass = true;
    	private List datas;
		public Workbook getWorkbook() {
			return workbook;
		}
		public void setWorkbook(Workbook workbook) {
			this.workbook = workbook;
		}
		public boolean isIspass() {
			return ispass;
		}
		public void setIspass(boolean ispass) {
			this.ispass = ispass;
		}
		public List getDatas() {
			return datas;
		}
		public void setDatas(List datas) {
			this.datas = datas;
		}
    }
    
    public static List<String> getTitleRow(InputStream is,String suffix) throws Exception {
    	Workbook workbook = ExcelUtil.getWorkBook(is,suffix);
    	if ( null == workbook) {
        	throw new RuntimeException("没有匹配的workbook");
        }
    	Sheet xssfSheet = workbook.getSheetAt(0);
        if (xssfSheet == null) {
        	throw new RuntimeException("sheet页获取失败");
        }
        Row xssfRow = xssfSheet.getRow(0);
        if ( null == xssfRow) {
        	return null;
        }
        List<String> tlist = new ArrayList<String>();
        for (int i =xssfRow.getFirstCellNum();i <xssfRow.getLastCellNum();i++  ) {
    		Cell cell = xssfRow.getCell(i);
    		tlist.add(getValue(cell));
    	}
        return tlist;
    }
    
    private static ValidateResult validateWorkBook(InputStream is,ObjectExcutor excutor,String suffix) throws Exception{
    	Workbook workbook = ExcelUtil.getWorkBook(is,suffix);
    	if ( null == workbook) {
        	throw new RuntimeException("没有匹配的workbook");
        }
    	Sheet xssfSheet = workbook.getSheetAt(0);
        if (xssfSheet == null) {
        	throw new RuntimeException("sheet页获取失败");
        }
        boolean ispass  =  true;
        List list = new ArrayList();
        Map objectMap = new HashMap();
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
        	Row xssfRow = xssfSheet.getRow(rowNum);
        	try {
                if (xssfRow != null) {
                	List<String> celllist= new ArrayList<String>();
                	int cellindex = 0;
                	
                	for (int i =xssfRow.getFirstCellNum();i <xssfRow.getLastCellNum();i++  ) {
                		Cell cell = xssfRow.getCell(i);
                		celllist.add(getValue(cell));
          			  	cellindex ++;
                	}
                	if (celllist.size()>0) {
	            	    try {
	              		    Object o = excutor.getObject(objectMap,celllist);
	              		    if ( null != o ) {
	              			    excutor.executeListAdd(objectMap,list,o);
	                	    }
	          		    }catch(Exception e) {
	          			    e.printStackTrace();
	          			    //处理出现的错误
	          			    Cell errorCell = xssfRow.createCell(cellindex,Cell.CELL_TYPE_STRING);
	          			    errorCell.setCellValue(e.getLocalizedMessage());
	          			    ispass = false;
	          		    }
                	}
                }
           }catch(Exception e) {
        	   e.printStackTrace();
        	   ispass = false;
        	   //将错误信息写到文件里面,xssfRow添加一列备注信息
        	   	  Iterator<Cell> cells = xssfRow.cellIterator();
             	  int cellindex = 0;
             	  if ( null != cells ) {
             		  while (cells.hasNext()) {
             			  cellindex ++;
             		  }
             	  }
        	   Cell errorCell = xssfRow.createCell(cellindex,Cell.CELL_TYPE_STRING);
 			   errorCell.setCellValue(e.getLocalizedMessage());
           }
        }
        return new ValidateResult(workbook, ispass, list);
    }
    
    
    /*万能处理方案：
   	所有日期格式都可以通过getDataFormat()值来判断
   	yyyy-MM-dd-----	14
   	yyyy年m月d日---	31
   	yyyy年m月-------	57
   	m月d日  ----------	58
   	HH:mm-----------	20
   	h时mm分  -------	32
     */
    private static String getDateValue(Cell cell ) {
		try {
        	//日期处理
        	short format = cell.getCellStyle().getDataFormat();  
            SimpleDateFormat sdf = null;  
            if(format == 14 || format == 31 || format == 57 || format == 58){  
                //日期  
                sdf = new SimpleDateFormat("yyyy-MM-dd");  
            }else if (format == 20 || format == 32) {  
                //时间  
                sdf = new SimpleDateFormat("HH:mm");  
            }  
            double value = cell.getNumericCellValue();  
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
            return sdf.format(date);  
		}catch(Exception e) {
			throw new RuntimeException();
		}
    }
    
    private static String getValue(Cell cell) {
    	String result = new String(); 
    	if ( null == cell) {
    		return null;
    	}
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型  
	    	if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
	    		return getDateValue(cell);
	    	}
        	
	    	//对比看看是不是int
	    	DecimalFormat df = new DecimalFormat("0");  
	    	result = df.format(cell.getNumericCellValue()); 
        	/**
	    	double value = cell.getNumericCellValue();
        	int ivalue = (int)value;
        	if (value == ivalue) {
        		result = String.valueOf(ivalue);
        	}else {
        		result = String.valueOf(value);
        	}*/
        	/**
        	try {
	        	CellStyle style = cell.getCellStyle();  
	        	DecimalFormat format = new DecimalFormat();  
	        	String temp = style.getDataFormatString();  
	        	// 单元格设置成常规  
	        	if (temp.equals("General")) {  
	              format.applyPattern("#");  
	        	}  
	        	result = format.format(value); 
        	}catch(Exception e) {
        		e.printStackTrace();
        		result = String.valueOf(value);
        	}*/
            break;  
        case HSSFCell.CELL_TYPE_STRING:// String类型  
            result = cell.getRichStringCellValue().toString();  
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
        	result = String.valueOf(cell.getBooleanCellValue());
        case HSSFCell.CELL_TYPE_BLANK:  
            result = "";  
        default:  
            result = cell.getStringCellValue();  
            break;  
        }  
        return result; 
    }
    
    public static void main(String[] args) {
		double d = 2016.60;
		int id = (int)d;
		System.out.println(d == id);
		//System.out.println(Integer.parseInt(d)));
		
		
		
	}
    
//  if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
//  SimpleDateFormat sdf = null;  
//  if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
//          .getBuiltinFormat("h:mm")) {  
//      sdf = new SimpleDateFormat("HH:mm");  
//  } else {// 日期  
//      sdf = new SimpleDateFormat("yyyy-MM-dd");  
//  }  
//  Date date = cell.getDateCellValue();  
//  result = sdf.format(date);  
//} else if (cell.getCellStyle().getDataFormat() == 58) {  
//  // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
//  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
//  double value = cell.getNumericCellValue();  
//  Date date = org.apache.poi.ss.usermodel.DateUtil  
//          .getJavaDate(value);  
//  result = sdf.format(date);  
//} else {  
//  double value = cell.getNumericCellValue();  
//  CellStyle style = cell.getCellStyle();  
//  DecimalFormat format = new DecimalFormat();  
//  String temp = style.getDataFormatString();  
//  // 单元格设置成常规  
//  if (temp.equals("General")) {  
//      format.applyPattern("#");  
//  }  
//  result = format.format(value);  
//}  
    
//    @SuppressWarnings("static-access")
//    private static String getValue(XSSFCell xssfRow) {
//    	return getValue(xssfRow);
//        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(xssfRow.getBooleanCellValue());
//        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
//            return String.valueOf(xssfRow.getNumericCellValue());
//        } else {
//            return String.valueOf(xssfRow.getStringCellValue());
//        }
//    }
   
//    @SuppressWarnings("static-access")
//    private static String getValue(HSSFCell hssfCell) {
//    	return getValue(hssfCell);
//        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(hssfCell.getBooleanCellValue());
//        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
//        	if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
//        		return getDateValue(hssfCell);
//        	}
//            return String.valueOf(hssfCell.getNumericCellValue());
//        } else {
//            return String.valueOf(hssfCell.getStringCellValue());
//        }
//    }
  
    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
//    private static ResponseInfo readXls(InputStream is,ExcelExcutor excutor) {
//        //InputStream is = new FileInputStream(path);
//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//        List list = new ArrayList();
//        // Read the Sheet
//        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
//            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//            if (hssfSheet == null) {
//                continue;
//            }
//            // Read the Row
//            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                if (hssfRow != null) {
//                	  Iterator<Cell> cells = hssfRow.cellIterator();
//	              	  if ( null != cells ) {
//	              		  List<String> celllist= new ArrayList<String>();
//	              		  while (cells.hasNext()) {
//	              			  Cell cell = cells.next();
//	              			  celllist.add(getValue(cell));
//	              		  }
//	              		  Object o = excutor.getObject(celllist);
//	              		  if ( null != o ) {
//	                    		  list.add(o);
//	                    	  }
//	              	  }
//                }
//            }
//        }
//        return list;
//    } 
    
//  public static ResponseInfo readFile(InputStream is,String suffix,ObjectExcutor excutor){
//	if (OFFICE_EXCEL_2003_POSTFIX.equals(suffix)) {
//        //return readXls(is,excutor);
//		return new ResponseInfo(new ResponseStatus(false,"不支持该格式文件,请用excel2010以上版本."), "不支持该格式文件,请用excel2010以上版本.");
//    } else if (OFFICE_EXCEL_2010_POSTFIX.equals(suffix)) {
//        return readXlsx(is,excutor);
//    }
//	return null;
//}
 
}