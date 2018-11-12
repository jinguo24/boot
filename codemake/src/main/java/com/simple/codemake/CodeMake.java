package com.simple.codemake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.simple.codemake.model.TableColumnSchema;
import com.simple.codemake.service.CodeMakeService;
import com.simple.codemake.util.PathUtil;
import com.simple.freemarker.common.FtlHandler;

@Component
public class CodeMake {

	@Autowired
	CodeMakeService codeMakeService;
	
    private static String upperFirst(String str)
    {
        if((StringUtils.isBlank(str)))
            return str;
        else
            return (new StringBuilder()).append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
    }
    
    private static String lowerFirst(String str)
    {
        if((StringUtils.isBlank(str)))
            return str;
        else
            return (new StringBuilder()).append(str.substring(0, 1).toLowerCase()).append(str.substring(1)).toString();
    }
    
    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {    
        if (dest.exists()) {
        	dest.delete();
        }
        File pfile = dest.getParentFile();
        if (!pfile.exists()) {
        	pfile.mkdirs();
        }
    	FileChannel inputChannel = null;    
        FileChannel outputChannel = null;    
	    try {
	        inputChannel = new FileInputStream(source).getChannel();
	        outputChannel = new FileOutputStream(dest).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } finally {
	        inputChannel.close();
	        outputChannel.close();
	    }
    }
    
    private boolean containsDefaultTable(List<String> tables) {
    	return (tables.contains("sys_permission")&&tables.contains("sys_role")&&tables.contains("sys_role_permission")
		&&tables.contains("sys_user_role")&&tables.contains("user_info"));
    }
    
    private boolean idDefaultTable(String table) {
    	return (table.equals("sys_permission")|| table.equals("sys_role")||table.equals("sys_role_permission")
    			||table.equals("sys_user_role")||table.equals("user_info"));
    }
    
    public void makeProjectFile(String project,String db) throws Exception {
    	ModelMap map = new ModelMap();
		map.put("projectName", project);
		map.put("dbName", db);
		
		List<String> tables = codeMakeService.getTables(db);
		if (containsDefaultTable(tables)) {
			//Project pom.xml
			String projectPomFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/projectPom.ftl";
			String outProjectPom = PathUtil.getRootParentPath() +project + "/pom.xml";
			FtlHandler.make(map, projectPomFtl, outProjectPom);
			//Project .gitignore
			String projectGitignoreFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/gitignore.ftl";
			String outProjectGitignore = PathUtil.getRootParentPath() +project + "/.gitignore";
			FtlHandler.make(map, projectGitignoreFtl, outProjectGitignore);
			
			//Service Project pom.xml
			String serviceProjectPomFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/serviceProjectPom.ftl";
			String outServiceProjectPom = PathUtil.getRootParentPath() +project + "/"+project+"Service/pom.xml";
			FtlHandler.make(map, serviceProjectPomFtl, outServiceProjectPom);
			
			//Admin Project pom.xml,application.yml,applicatoin-dev.yml,application-prod.yml
			String adminProjectPomFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/adminProjectPom.ftl";
			String outAdminProjectPom = PathUtil.getRootParentPath() +project + "/"+project+"Admin/pom.xml";
			FtlHandler.make(map, adminProjectPomFtl, outAdminProjectPom);
			
			String appYmlFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/applicationYml.ftl";
			String outAdminAppYml = PathUtil.getRootParentPath() +project + "/"+project+"Admin/src/main/resources/application.yml";
			FtlHandler.make(map, appYmlFtl, outAdminAppYml);
			
			String appDevYmlFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/applicationDevYml.ftl";
			String outAdminAppDevYml = PathUtil.getRootParentPath() +project + "/"+project+"Admin/src/main/resources/application-dev.yml";
			FtlHandler.make(map, appDevYmlFtl, outAdminAppDevYml);
			
			String appProdYmlFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/applicationProdYml.flt";
			String outAdminAppProdYml = PathUtil.getRootParentPath() +project + "/"+project+"Admin/src/main/resources/application-prod.yml";
			FtlHandler.make(map, appProdYmlFtl, outAdminAppProdYml);
			
			//复制Shiro
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/config/RedisConfig.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/config/RedisConfig.java"));
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/config/ShiroConfig.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/config/ShiroConfig.java"));
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/shiro/MyShiroRealm.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/shiro/MyShiroRealm.java"));
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/shiro/PasswordHelper.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/shiro/PasswordHelper.java"));
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/shiro/ShiroService.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/shiro/ShiroService.java"));
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/shiro/UserSession.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/shiro/UserSession.java"));
			//复制Application
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/UserApplication.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/UserApplication.java"));
			
			//Api Project pom.xml,application.yml,applicatoin-dev.yml,application-prod.yml
			String apiProjectPomFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/apiProjectPom.ftl";
			String outApiProjectPom = PathUtil.getRootParentPath() +project + "/"+project+"Api/pom.xml";
			FtlHandler.make(map, apiProjectPomFtl, outApiProjectPom);
			
			String outApiAppYml = PathUtil.getRootParentPath() +project + "/"+project+"Api/src/main/resources/application.yml";
			FtlHandler.make(map, appYmlFtl, outApiAppYml);
			
			String outApiAppDevYml = PathUtil.getRootParentPath() +project + "/"+project+"Api/src/main/resources/application-dev.yml";
			FtlHandler.make(map, appDevYmlFtl, outApiAppDevYml);
			
			String outApiAppProdYml = PathUtil.getRootParentPath() +project + "/"+project+"Api/src/main/resources/application-prod.yml";
			FtlHandler.make(map, appProdYmlFtl, outApiAppProdYml);
			
			copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/UserApplication.java"),
					new File(PathUtil.getRootParentPath() +project +"/"+project+"Api/src/main/java/com/simple/UserApplication.java"));
		}else {
			throw new RuntimeException("DB["+db+"] must contain tables[sys_permission，sys_role,sys_role_permission,sys_user_role,user_info]");
		}
    }
    
	public void make(String project,String db,boolean isall,List<String> tables) throws Exception{
		if (StringUtils.isBlank(db)) {
			throw new RuntimeException("db is null.");
		}
		if (StringUtils.isBlank(project)) {
			throw new RuntimeException("project is null.");
		}
		
		if (isall) {
			tables = codeMakeService.getTables(db);
		}else if (null == tables || tables.size()==0) {
			throw new RuntimeException("tables is null.");
		}
		
		for (String tb:tables) {
			//每个表生成
			String simpleClassName = upperFirst(tb);
			List<TableColumnSchema> schmas = codeMakeService.getTableColumnSchema(db, tb);
			if (schmas == null || schmas.size() == 0)
			{
				continue;
			}
			ModelMap map = new ModelMap();
			map.put("projectName", project);
			map.put("dbName", db);
			map.put("entityName", PathUtil.getHumpWithUnderLine(simpleClassName, true));
			map.put("entityInstanceName", lowerFirst(PathUtil.getHumpWithUnderLine(simpleClassName, false)));
			map.put("entityPackageName", "com.simple.domain.po");
			map.put("mapperPackageName", "com.simple.mapper");
			map.put("servicePackageName", "com.simple.service");
			map.put("serviceImplPackageName", "com.simple.service.impl");
			map.put("controllerPackageName", "com.simple.controller");
			map.put("tableName", tb);
			map.put("key", "key-value");
			map.put("columns", schmas);
			for (TableColumnSchema tcs : schmas) {
				if (tcs.getColumnName().equals("id")) {
					map.put("pk", tcs);
					break;
				}
			}
			
			//model生成到service项目
			String modelFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/model_format.ftl";
			String outJava = PathUtil.getRootParentPath() +project + "/"+project+"Service/src/main/java/com/simple/domain/po/"+ PathUtil.getHumpWithUnderLine(simpleClassName, true) + ".java";
			FtlHandler.make(map, modelFtl, outJava);
			
			//Mapper生成到service项目
			String mapperFtl = PathUtil.getMakeProjectRootPath()+ "src/main/resources/ftl/Mapper.ftl";
			String mapperOutJava = PathUtil.getRootParentPath() +project + "/"+project+"Service/src/main/java/com/simple/mapper/"+ PathUtil.getHumpWithUnderLine(simpleClassName, true) + "Mapper.java";
			FtlHandler.make(map, mapperFtl, mapperOutJava);
			
			//mapper.xml生成到service项目
			String sqlMapFtl = PathUtil.getMakeProjectRootPath() + "src/main/resources/ftl/TableSqlMap.ftl";
			String outSqlMap = PathUtil.getRootParentPath() +project + "/"+project+"Service/src/main/resources/mapper/" + PathUtil.getHumpWithUnderLine(simpleClassName, true) + "Mapper.xml";
			FtlHandler.make(map, sqlMapFtl, outSqlMap);
			
			//Service生成到service项目
			String serviceFtl = PathUtil.getMakeProjectRootPath() + "src/main/resources/ftl/Service.ftl";
			String serviceOutJava = PathUtil.getRootParentPath() +project + "/"+project+"Service/src/main/java/com/simple/service/" + PathUtil.getHumpWithUnderLine(simpleClassName, true) + "Service.java";
			FtlHandler.make(map, serviceFtl, serviceOutJava);
			
			//ServiceImpl生成到service项目
			String serviceImplFtl = PathUtil.getMakeProjectRootPath() + "src/main/resources/ftl/ServiceImpl.ftl";
			String serviceImplOutJava = PathUtil.getRootParentPath() +project + "/"+project+"Service/src/main/java/com/simple/service/impl/" + PathUtil.getHumpWithUnderLine(simpleClassName, true) + "ServiceImpl.java";
			FtlHandler.make(map, serviceImplFtl, serviceImplOutJava);			

			//sys_permission，sys_role,sys_role_permission,sys_user_role,user_info忽略，生成固定的controller
			if (!idDefaultTable(tb)) {
				//Controller admin和api都生成
				String controllerFtl = PathUtil.getMakeProjectRootPath() + "src/main/resources/ftl/Controller.ftl";
				String adminControllerOutJava = PathUtil.getRootParentPath() +project + "/"+project+"Admin/src/main/java/com/simple/controller/" + PathUtil.getHumpWithUnderLine(simpleClassName, true) + "Controller.java";
				FtlHandler.make(map, controllerFtl, adminControllerOutJava);	
				
				String apiControllerOutJava = PathUtil.getRootParentPath() +project + "/"+project+"Api/src/main/java/com/simple/controller/" + PathUtil.getHumpWithUnderLine(simpleClassName, true) + "Controller.java";
				FtlHandler.make(map, controllerFtl, apiControllerOutJava);
			}
		}
		
		//生成权限controller
		copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/controller/HomeController.java"),
				new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/controller/HomeController.java"));
		copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/controller/RoleController.java"),
				new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/controller/RoleController.java"));
		copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/controller/SysPermissionController.java"),
				new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/controller/SysPermissionController.java"));
		copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/controller/UserInfoController.java"),
				new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/controller/UserInfoController.java"));
		//复制BaseController
		copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/controller/BaseController.java"),
				new File(PathUtil.getRootParentPath() +project +"/"+project+"Admin/src/main/java/com/simple/controller/BaseController.java"));
		copyFileUsingFileChannels(new File(PathUtil.getMakeProjectRootPath()+"/src/main/resources/initFile/controller/BaseController.java"),
				new File(PathUtil.getRootParentPath() +project +"/"+project+"Api/src/main/java/com/simple/controller/BaseController.java"));
	}
}
