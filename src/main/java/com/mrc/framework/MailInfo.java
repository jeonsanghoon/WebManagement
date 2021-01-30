package com.mrc.framework;

import com.mrc.db.dto.common.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedHashMap;

/**
 * 메일보내기
 * @param <T>
 */
@Component
public class MailInfo<T> {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	public MailInfo(){}
	public String sendMail(MailData mail) {
		return sendMail(mail,null);
	}
	public String sendMail(MailData mail, T data) {

		MailInfoThreadExec m = new MailInfoThreadExec();
		//m.sendMail(mail, data, javaMailSender, templateEngine);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				m.sendMail(mail, data, javaMailSender, templateEngine);
				
			}
		});
		
		thread.start();
		thread.setUncaughtExceptionHandler(new ThreadExceptionEmailHander(mail, data));
		return "";
	}
}


class  ThreadExceptionEmailHander implements UncaughtExceptionHandler{
	private MailData _mail;
	private Object _mailParam;
	public  ThreadExceptionEmailHander(MailData mail, Object param) {
		this._mail = mail;
		this._mailParam = param;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		System.out.println(thread.getName() + ">>> 메일보내기 오류 MailInfo => " 
			+ Global.getDataInfo().convertToString(_mail)
			+ " || MailParam => " 
			+ ((_mailParam == null) ? "" :Global.getDataInfo().convertToString(_mailParam))
			+ " => " + e);
		;
	}

}

class MailInfoThreadExec{

	public MailInfoThreadExec() {}
	@Value("webservice@altsoft.kr")
	private String defaultFromMail;

	public String sendMail(MailData mail, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
		return this.sendMail(mail, null, javaMailSender, templateEngine);
	}

	public String sendMail(MailData mail, Object data,JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			System.out.println(">>>>>>1");
			// 메일제목 석정
			helper.setSubject(mail.getTitle());

			System.out.println(">>>>>>2");
			helper.setFrom(new InternetAddress(mail.getFromMail(), mail.getFromMailName()));

			/* 받는 사람 */
			String[] arrToMail = mail.getToMail().split(",");
			String[] arrToMailName = mail.getToMailName().split(",");
			InternetAddress[] toAddr = new InternetAddress[arrToMail.length];
			for (int i = 0; i < arrToMail.length; i++) {
				if (i > arrToMailName.length - 1 )
					toAddr[i] = new InternetAddress(arrToMail[i]);
				else
					toAddr[i] = new InternetAddress(arrToMail[i], arrToMailName[i]);
			}
			message.setRecipients(Message.RecipientType.TO, toAddr);
			System.out.println(">>>>>>3");
			/* 참조하는 사람 */
			if (mail.getCcMail() != null) {
				String[] arrCcMail = mail.getCcMail().split(",");
				String[] arrCcMailName = mail.getCcMailName().split(",");
				InternetAddress[] ccAddr = new InternetAddress[arrCcMail.length];
				for (int i = 0; i < arrCcMail.length; i++) {
					if (i > arrCcMailName.length - 1)
						ccAddr[i] = new InternetAddress(arrCcMail[i]);
					else
						ccAddr[i] = new InternetAddress(arrCcMail[i], arrCcMailName[i]);
				}
				message.setRecipients(Message.RecipientType.CC, ccAddr);
			}
			System.out.println(">>>>>>4");
			// 템플릿에 전달할 데이터
			if (Global.getDataInfo().isNullEmpty(mail.getTempleateName())) {
				mail.setTempleateName("/templeate/mail-template.html");
			}
			System.out.println(">>>>>>5");
			// message.setRecipients(Message.RecipientType., addresses);

			Context context = new Context();
			if (data == null)
				context.setVariable("message", mail.getMessage());
			else {
				LinkedHashMap<String, Object> map = Global.getDataInfo().convertClassToMap(data);
				for (String key : map.keySet()) {
					if (map.get(key) == null)
						break;

					context.setVariable(key, map.get(key).toString());
				}
			}
			System.out.println(">>>>>>6");
			helper.setText(templateEngine.process(mail.getTempleateName(), context), true);
			System.out.println(">>>>>>7");
			javaMailSender.send(message);
			System.out.println(">>>>>>8");

		} catch (MessagingException e) {

			return e.getMessage();
		} catch (Exception ex) {
			return ex.getMessage();
		}

		return "";
	}

	/*@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println(">>> 메일보내기  => " 
				+ Global.getDataInfo().convertToString(_mail)
				+ " || MailParam => " 
				+ ((this._mail == null) ? "" :Global.getDataInfo().convertToString(this._param))
			);
			;
		this.sendMail(this._mail,this._param);
	}*/
	
}