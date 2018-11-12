package com.simple.codemake.util;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

public class PathUtil
{

	private static final String makeproject = "codemake";
	
	public static String getMakeProjectRootPath()
	{
		String opath = getOpath();
		if (opath.contains("/target/test-classes/"))
		{
			return opath.split("target/test-classes/")[0];
		} else if (opath.endsWith("/conf/"))
		{
			return opath;
		} else if (opath.contains("/target/classes/"))
		{
			return opath.split("target/classes/")[0];
		}
		{
			return null;
		}
	}
	
	public static String getRootPath()
	{
		String opath = getOpath();
		if (opath.contains("/"+makeproject+"/target/test-classes/"))
		{
			return opath.split(makeproject+"/target/test-classes/")[0];
		} else if (opath.endsWith("/conf/"))
		{
			return opath;
		} else if (opath.contains("/"+makeproject+"/target/classes/"))
		{
			return opath.split(makeproject+"/target/classes/")[0];
		}
		{
			return null;
		}
	}
	
	public static String getRootParentPath() {
		String opath = getOpath();
		if (opath.contains("/"+makeproject+"/target/test-classes/"))
		{
			String path = opath.split(makeproject+"/target/test-classes/")[0];
			File fp = new File(path);
			return fp.getParent().replaceAll("\\\\", "/") + "/";
		} else if (opath.endsWith("/conf/"))
		{
			return opath;
		} else if (opath.contains("/"+makeproject+"/target/classes/"))
		{
			String path = opath.split(makeproject+"/target/classes/")[0];
			File fp = new File(path);
			return fp.getParent().replaceAll("\\\\", "/") + "/";
		}
		{
			return null;
		}
	}

	private static String getOpath()
	{
		try
		{
			return  new File(PathUtil.class.getResource("/").toURI()).getAbsolutePath().replaceAll("\\\\", "/") + "/";
		} catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String getHumpWithUnderLine(String context,boolean firstUpper) {
		return getHumpWithSymbol(context,"_",firstUpper);
	}
	
	public static String getHumpWithMiddleLine(String context,boolean firstUpper) {
		return getHumpWithSymbol(context,"-",firstUpper);
	}
	
	private static String getHumpWithSymbol(String arg,String symbol,boolean firstUpper) {
		if (!StringUtils.isBlank(arg)) {
			String[] args = arg.split(symbol);
			if (null != args) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0 ; i < args.length; i ++) {
					if (StringUtils.isBlank(args[i])) continue;
					if (i==0) {
						if (firstUpper) {
							sb.append(args[i].substring(0, 1).toUpperCase())
							.append(args[i].substring(1));
						}else {
							sb.append(args[i]);
						}
					}else {
						sb.append(args[i].substring(0, 1).toUpperCase())
						.append(args[i].substring(1));
					}
				}
				return sb.toString();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getRootPath());
		System.out.println(getHumpWithUnderLine("adfasdf_adsfadsf_afdadsf",true));
		System.out.println(getHumpWithUnderLine("adfasdf_adsfadsf_afdadsf",false));
		
		String opath = getOpath();
		System.out.println(opath);
		if (opath.contains("/"+makeproject+"/target/classes/"))
		{
			String path = opath.split(makeproject+"/target/classes/")[0];
			File fp = new File(path);
			System.out.println(fp.getParent().replaceAll("\\\\", "/"));
		}
	}
	
}
