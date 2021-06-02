package com.se.dungcuthethao.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${mail.sender.name}")
	private String senderName;

	@Override
	public void sendEmail(Mail mail) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(new InternetAddress(mail.getMailFrom(), new String(senderName.getBytes("ISO-8859-1"), "UTF-8")));
			helper.setTo(mail.getMailTo());
			helper.setSubject(mail.getMailSubject());
			helper.setText(mail.getMailContent(), true);
			
			javaMailSender.send(helper.getMimeMessage());
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
