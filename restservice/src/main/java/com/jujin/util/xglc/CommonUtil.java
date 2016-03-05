package com.jujin.util.xglc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 
* <p>Title: CommonUtil.java</p>
* <p>Description:工具类(西瓜理财) </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: jujinziben</p>
* @author zhengshaoxu
* @date 2015年10月21日
* @version 1.0
 */
public class CommonUtil {
	
	/**
	 * 随机获取32位UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	


	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 *            
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}
	
	
	/**
	 * date转string（yyyy-MM-dd HH:mm:ss）
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}else{
			return null;
		}
	}
	
	/**
	 * string转date（yyyy-MM-dd HH:mm:ss）
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String str) throws ParseException{
		if(str!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(str);
		}else{
			return null;
		}
	}
	
	
	/**
	 * 获取随机数
	 * @return
	 */
	public static int getRandom(int min,int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	
	
	/**
	 * 金钱格式化(0.00)
	 * @param s
	 * @return
	 */
	public static String fommatHFMoney(String s){
		NumberFormat nf = new DecimalFormat("0.00");
		return nf.format(Double.valueOf(s));
	}
	
	/**
	 * 金钱格式化(###,##0.00)
	 * @param s
	 * @return
	 */
	public static String fommatPageMoney(String s){
		NumberFormat nf = new DecimalFormat("###,##0.00");
		return nf.format(Double.valueOf(s));
	}
	
	/**
	 * 
	* Title: getYesterdayStr
	* Description: 获取昨天
	* @param dateStr
	* @return
	* @throws ParseException
	 */
	public static String getYesterdayStr(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,   -1);
		Date yesterday = cal.getTime();
		return sdf.format(yesterday);
	}
	
	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isInDate(Date date, String strDateBegin,String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean rs = false;
		try {
			String dayStr = sdf.format(date);
			Date begin = stringToDate(dayStr + " " + strDateBegin);
			Date end = stringToDate(dayStr + " " + strDateEnd);
			if(date.after(begin) && date.before(end)){
				rs = true;
			}else{
				rs = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rs;
		
	}
	

}