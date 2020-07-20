package com.dev.utils.email;

import com.dev.model.email.EmailModel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created on 2019-08-02 16:06.
 *
 * @author zgq7
 */
public class MailSendUtils {

	private static final Logger logger = LoggerFactory.getLogger(MailSendUtils.class);
	/**
	 * 发送者姓名
	 **/
	private static final String posterName = "廖南洲";
	/**
	 * 发送者地址
	 *
	 * @Value 不能与 static 混用，否则变量无法被@Value赋值
	 **/
	@Value("${spring.mail.username}")
	private String posterAdress;
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;

	/**
	 * 文本发送
	 **/
	public void sendEmailAsText(final EmailModel emailModel) {
		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
			mimeMessage.setFrom(posterAdress);
			mimeMessage.setRecipients(Message.RecipientType.TO, emailModel.getRecieverEmailAddress());
			mimeMessage.setText("<html><body>"
					+ "hello：" + emailModel.getRecieverName()
					+ "<br>" + "msg：" + emailModel.getEmailContent()
					+ "<br>" + "from :" + posterName
					+ "</body></html>");
		};
		try {
			this.javaMailSender.send(mimeMessagePreparator);
			logger.info("邮箱已返送至[{}]邮箱！", emailModel.getRecieverName());
		} catch (MailException e) {
			logger.error("邮箱异常：", e);
		}
	}

	/**
	 * html 网页发送
	 * 该方法为同步方法
	 **/
	public void sendEmailAsSysExceptionHtml(final EmailModel emailModel) {
		MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			// 发送者
			mimeMessageHelper.setFrom(posterAdress);
			// 接收者
			mimeMessageHelper.setTo(emailModel.getRecieverEmailAddress());
			// 发送时间
			mimeMessageHelper.setSentDate(new Date());
			// 邮件主题
			mimeMessageHelper.setSubject("测试主题");
			// 附件
			mimeMessageHelper.addAttachment("20200425儿保医生数据统计.xlsx", FileUtils.getFile("C:\\Users\\Vdaifu\\Desktop\\20200425儿保医生数据统计.xlsx"));
			// 文本内容
			mimeMessageHelper.setText("<!DOCTYPE html>\n" +
							"<html>\n" +
							"\t<head>\n" +
							"\t\t<meta charset=\"UTF-8\">\n" +
							"\t\t<title></title>\n" +
							"\t</head>\n" +
							"\t<style>\n" +
							"\t\tbody,\n" +
							"\t\ttable,\n" +
							"\t\ttbody,\n" +
							"\t\ttr {\n" +
							"\t\t\tbackground-color: aquamarine;\n" +
							"\t\t\tbackground-size: 100%;\n" +
							"\t\t}\n" +
							"\t</style>\n" +
							"\n" +
							"\t<body>\n" +
							"\t\t<table border=\"solid 2 px\" align=\"center\" style=\"text-align: center;\">\n" +
							"\t\t\t<tbody>\n" +
							"\t\t\t\t<tr>\n" +
							"\t\t\t\t\t<td width=\"200px\" bgcolor=\"coral\">时间</td>\n" +
							"\t\t\t\t\t<td width=\"80%\" bgcolor=\"azure\">" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td>\n" +
							"\t\t\t\t</tr>\n" +
							"\t\t\t\t<tr>\n" +
							"\t\t\t\t\t<td width=\"200px\" bgcolor=\"coral\">信息</td>\n" +
							"\t\t\t\t\t<td width=\"80%\" bgcolor=\"azure\">" + "测试" + "</td>\n" +
							"\t\t\t\t</tr>\n" +
							"\t\t\t\t<tr>\n" +
							"\t\t\t\t\t<td width=\"200px\" bgcolor=\"coral\">堆栈</td>\n" +
							"\t\t\t\t\t<td width=\"80%\" bgcolor=\"azure\" style=\"text-align: left;\">" + emailModel.getEmailContent() + "</td>\n" +
							"\t\t\t\t</tr>\n" +
							"\t\t\t</tbody>\n" +
							"\t\t</table>\n" +
							"\t</body>\n" +
							"\n" +
							"</html>"
					, true);

			this.javaMailSender.send(mimeMessage);
			logger.info("邮箱已返送至[{}]邮箱！", emailModel.getRecieverName());

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MailException e) {
			logger.error("邮箱异常：", e);
		}
	}

	public class htmlInstance implements Runnable {
		@Override
		public void run() {
			MailSendUtils mailSendUtils = new MailSendUtils();
			mailSendUtils.sendEmailAsSysExceptionHtml(new EmailModel());
		}
	}


}
