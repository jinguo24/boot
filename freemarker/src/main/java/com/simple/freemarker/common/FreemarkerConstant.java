package com.simple.freemarker.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FreemarkerConstant {

	public static final DateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final int FULL_DATE_LENGTH = 19;

	public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final int SHORT_DATE_LENGTH = 10;
}
