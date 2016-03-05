package com.jujin.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * 使用apache commons mail开源项目发送邮件示例
 * http://commons.apache.org/proper/commons-email/
 * 
 * @author
 */
public class BugUtil {

	private static final String HOSTNAME = "smtp.sina.com";
	private static final String POP_USERNAME = "xxbcoder@sina.com";
	private static final String USERNAME = "bug"; // 个人姓名
	private static final String POP_PASSWORD = "13674948769";
	private static final String CODING = "UTF-8";


	public static void sendBugEmail(String errorMsg,Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		simpleEmail(errorMsg,exception);
	}

	/**
	 * 
	 * @Title: simpleEmail
	 * @Description: TODO
	 * @param @param toEmail ：收件人地址
	 * @param @param subject ：主题
	 * @param @param msg ：内容
	 * @return void
	 * @throws
	 */
	private static void simpleEmail(String errorMsg,String msg) {
		SimpleEmail email = new SimpleEmail();
		email.setHostName(HOSTNAME);
		email.setAuthentication(POP_USERNAME, POP_PASSWORD);// 邮件服务器验证：用户名/密码
		email.setCharset(CODING);// 必须放在前面，否则乱码
		try {
			email.addTo("zhengshaoxu@jujinziben.com");
			email.setFrom(POP_USERNAME, USERNAME);
			email.setSubject(errorMsg);
			email.setMsg(msg);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: multiPartEmail
	 * @Description: TODO
	 * @param @param toEmail ：收件人地址
	 * @param @param subject ：主题
	 * @param @param msg ：内容
	 * @param @param filePath ：附件路径
	 * @param @param fileName ：附件名
	 * @return void
	 * @throws
	 */
	public static void multiPartEmail(String toEmail, String subject,
			String msg, String filePath, String fileName) {
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(HOSTNAME);
		email.setAuthentication(POP_USERNAME, POP_PASSWORD);
		email.setCharset(CODING);
		try {
			email.addTo(toEmail);
			email.setFrom(POP_USERNAME, USERNAME);
			email.setSubject(subject);
			email.setMsg(msg);
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath("d:/student_templet.xls");// 本地文件
			// attachment.setURL(new URL("filePath"));//远程文件filePath
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("答辩学生名单");
			attachment.setName("student_templet.xls");// fileName

			email.attach(attachment);
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: htmlEmail
	 * @Description: TODO
	 * @param @param toEmail ：收件人地址
	 * @param @param subject ：主题
	 * @param @param msg ：内容
	 * @return void
	 * @throws
	 */
	public static void htmlEmail(String toEmail, String subject, String msg) {

		HtmlEmail email = new HtmlEmail();
		email.setHostName(HOSTNAME);
		email.setAuthentication(POP_USERNAME, POP_PASSWORD);
		email.setCharset(CODING);
		try {
			email.addTo(toEmail);
			email.setFrom(POP_USERNAME, USERNAME);
			email.setSubject(subject);
			email.setHtmlMsg("<b>关于论文最后答辩时间</b><br/><div>2013-05-18</div>");
			// email.setHtmlMsg(msg);
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}