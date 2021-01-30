package com.mrc.framework;

import com.mrc.db.dto.common.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.LinkedHashMap;

public class MailInfoThread  implements Runnable{

	
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("webservice@altsoft.kr")
	private String defaultFromMail;

	MailData _mail;
	Object _param;
	public MailInfoThread(MailData mail) {
		this._mail = mail;
		//this.sendMail(mail);
	}
	
	public <T>MailInfoThread(MailData mail, T data) {
		this._mail = mail;
		this._param = data;
	}
	public String sendMail(MailData mail) {
		return this.sendMail(mail, null);
	}

	public <T> String sendMail(MailData mail, T data) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			// 메일제목 석정
			helper.setSubject(mail.getTitle());

			// 송신자 설정
			/*if (Global.DataInfo.isNullEmpty(mail.getFromMail())) {
				mail.setFromMail(defaultFromMail);
				helper.setFrom(new InternetAddress(defaultFromMail, "온닫이 고객센터"));
			}
			else {
				helper.setFrom(new InternetAddress(mail.getFromMail(), mail.getFromMailName()));
			}*/
			
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

			// 템플릿에 전달할 데이터
			if (Global.getDataInfo().isNullEmpty(mail.getTempleateName())) {
				mail.setTempleateName("/templeate/mail-template.html");
			}

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

			helper.setText(templateEngine.process(mail.getTempleateName(), context), true);

			javaMailSender.send(message);

		} catch (MessagingException e) {

			return e.getMessage();
		} catch (Exception ex) {
			return ex.getMessage();
		}

		return "";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.sendMail(this._mail,this._mail);
	}

}