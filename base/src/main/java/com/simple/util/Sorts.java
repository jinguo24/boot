package com.simple.util;

import com.google.common.collect.Lists;
import com.simple.error.ErrorCode;
import com.simple.error.SystemException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenkaixiang on 2017/8/25.
 */
public class Sorts {


	private static final String ASC_STR = "+";
	private static final String DESC_STR = "-";
	private static final String COMMA = ",";

	private String name;
	private String type;

	private Sorts() {
	}

	public Sorts(String name, String type) {
		this.name = CamelConvertUtil.camel2Underline(name);
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = CamelConvertUtil.camel2Underline(name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static List<Sorts> parseRequestSort(String sortStr) {
		List<Sorts> sorts = new ArrayList<>();
		if (StringUtils.isNotBlank(sortStr)) {
			for (String s : sortStr.split(COMMA)) {
				Sorts sort = null;
				if (s.startsWith(ASC_STR)) {
					sort = new Sorts(s.substring(1), "ASC");
				}
				if (s.startsWith(DESC_STR)) {
					sort = new Sorts(s.substring(1), "DESC");
				}
				sorts.add(sort);
			}
		}
		return sorts;
	}

	public static String getSortsString(String sortStr, List<String> range) {
		if (StringUtils.isBlank(sortStr)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String s : sortStr.split(COMMA)) {
			if (sb != null && sb.length() > 1) {
				sb.append(", ");
			}
			if (range != null && !range.contains(s.substring(1))) {
				throw new SystemException(ErrorCode.PARAMETER_ERROR);
			}
			sb.append(CamelConvertUtil.camel2Underline(s.substring(1)));
			sb.append(s.startsWith(ASC_STR) ? " ASC" : " DESC");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(Sorts.getSortsString("createAt,-updateAt", Lists.newArrayList("1", "2")));
	}
}
