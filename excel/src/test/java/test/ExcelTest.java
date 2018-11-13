package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.simple.common.excel.DownLoadExcel;
import com.simple.common.excel.DownLoadExcutor;
import com.simple.common.excel.EntityCellValues;
import com.simple.common.excel.ObjectExcutor;
import com.simple.common.excel.ReadExcel;
import com.simple.common.excel.ResponseInfo;

public class ExcelTest {
	
	/**
	 * 下载示例,会直接输出到response中，页面直接弹出下载
	 * 只需要初始化好需要下载的list,然后在逻辑中根据list里面的对象填充好每列
	 * @param response
	 */
	public static void download(HttpServletResponse response) {
		final String[] titles = new String[]{"姓名","手机号","身份证号码","性别","微信","QQ","支付宝"};
		List<People> plist = new ArrayList<People>();
		for (int i = 0 ; i< 10; i ++) {
			People p = new People("jjj"+i, "123678766"+i, "12231sdsd2e12"+i, "234sdf"+i, "123123"+i, "asd12ss3"+i);
			plist.add(p);
		}
		DownLoadExcel.download(plist, Arrays.asList(titles), new DownLoadExcutor(){

			@Override
			public List<EntityCellValues> getCellValues(Object o) {
				People p = (People) o;
				List<String> sl = new ArrayList<String>();
				sl.add(p.getName());
				sl.add(p.getPhone());
				sl.add(p.getCardNo());
				//TODO 通过身份证获取性别
				sl.add("sx");
				
				sl.add(p.getWx());
				sl.add(p.getQq());
				sl.add(p.getZfb());
				//此处返回list对象，满足查询的一条数据需要拆分成多个对象
				//>>>1，不拆分对象 
				List<EntityCellValues> list = new ArrayList<EntityCellValues>();
				EntityCellValues ecv = new EntityCellValues();
				ecv.setO(p);
				ecv.setCellValues(sl);
				list.add(ecv);
				return list;
				
				//>>>2,根据业务拆分成多个对象，比如phone字段存储了多个电话，导出时一个电话导出一条
				//List<EntityCellValues> mulitylist = new ArrayList<EntityCellValues>();
				//String[] phones = p.getPhone().split(",");
				//for (String ph : phones) {
				//	EntityCellValues ecv0 = new EntityCellValues();
				//  People p0 = new People();
				//  p0.setNmae(p.getName());
				//  p0.setPhone(ph);
				//	ecv0.setO(p0);
				//	sl.set(1, ph);
				//	ecv0.setCellValues(sl);
				//	mulitylist.add(ecv);
				//}
				//return mulitylist;
			}
		}, response);
	}
	
	
	/**
	 * 上传文件,如果上传错误，则会在原来的excel中备注错误信息，然后以流的形式来下载
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public static String validForUpload(File file,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String suffix = file.getName().substring(file.getName().lastIndexOf(".")+1);
		ResponseInfo ri = ReadExcel.readReturnWorkBook(new FileInputStream(file),new ObjectExcutor(){
				@Override
				public Object getObject(Map objectMap,List<String> cellValues) {
						String name = cellValues.get(0);
						String phone = cellValues.get(1);
						String cardNo = cellValues.get(2);
						People p = new People();
						StringBuffer errormsg = new StringBuffer();
						if (StringUtils.isEmpty(cardNo)) {
							errormsg.append("身份证号不能为空");
						}
						
						if (StringUtils.isEmpty(name)) {
							errormsg.append("姓名不能为空");
						}
						
						p.setCardNo(cardNo);
						p.setName(name);
						p.setPhone(phone);
						
						
						if (errormsg.length()>0) {
							//此处抛出的错误提示信息会在excel中写出来
							throw new RuntimeException(errormsg.toString());
						}else {
							//此处设置objectMap,在重写executeListAdd的时候，需要查询出list之前的对象，根据objectMap来查询
							objectMap.put(p.getCardNo(), p);
							return p;
						}
				}

				/**
				 * 可不重写，默认执行list.add(o)
				 * 如果数据需要合并等等其他操作，需重写该方法
				 */
				@Override
				public void executeListAdd(Map objectMap, List list, Object o) {
					People p = (People)o;
					//查询已经存在的
					People oldPerson = (People) objectMap.get(p.getCardNo());
					if (list.contains(p)) {
						//list中已经存在该cardNo对象了，实际是oldPerson
						oldPerson.setPhone(oldPerson.getPhone()+","+p.getPhone());
					}else {
						list.add(p);
					}
					super.executeListAdd(objectMap, list, o);
				}
				
				
			},suffix);
		boolean isSuccess = ri.getStatus().getState();
		if (isSuccess) {
			//如果校验成功，则将数据写入到数据库
			List<People> ps = (List<People>) ri.getData();
			//TODO 将对象进行存储
			
			return "success.jsp";//
		}else {
			//如果失败，则将错误文件提供下载
			return ReadExcel.getErrorLogs((Workbook) ri.getData(), suffix, response);
		}
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static class People {
		public People(String name, String phone, String cardNo, String wx,
				String qq, String zfb) {
			this.name = name;
			this.phone = phone;
			this.cardNo = cardNo;
			this.wx = wx;
			this.qq = qq;
			this.zfb = zfb;
		}
		
		public People() {
		}
		private String name;
		private String phone;
		private String cardNo;
		private String wx;
		private String qq;
		private String zfb;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getWx() {
			return wx;
		}
		public void setWx(String wx) {
			this.wx = wx;
		}
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public String getZfb() {
			return zfb;
		}
		public void setZfb(String zfb) {
			this.zfb = zfb;
		}
		
		@Override
		public boolean equals(Object obj) {
			People p = (People) obj;
			if (this.cardNo.equals(p.getCardNo())) {
				return true;
			}
			return false;
		}
	}
}
