package com.jujin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Toolkit {

	private static String REGEX = "\\<[^>]*\\>*";

	private static String REPLACE = "";

	public static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String FormatDate(Date date) {
		if (date == null)
			return "";
		return format.format(date);
	}

	public static String RemoveLink(String link) {

		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(link); // 获得匹配器对象
		String content = m.replaceAll(REPLACE);

		return content;
	}

	// <a
	// href="http://www.jujinziben.com/borrowinfo.page?borrow_id=2014092700000000000000001141">

}
