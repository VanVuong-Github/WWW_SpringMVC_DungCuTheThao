package com.se.dungcuthethao.mail;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.se.dungcuthethao.entity.HoaDon;
import com.se.dungcuthethao.entity.KhachHang;

@Aspect
@Component
public class MailAspect {
	
	@Autowired
	private MailService mailService;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	@AfterReturning(pointcut = "execution(* com.se.dungcuthethao.controller.HoaDonController.add(..))", returning = "responseEntity")
	public void afterOrder(JoinPoint joinPoint, ResponseEntity<?> responseEntity) {
		HoaDon hoaDon = (HoaDon) joinPoint.getArgs()[0];
		KhachHang khachHang = hoaDon.getKhachHang();
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			new Thread( () -> {
				Mail mail = new Mail();
				mail.setMailFrom(mailFrom);
				mail.setMailTo(khachHang.getEmail());
				mail.setMailSubject("Đặt hàng thành công tại V-A-L shop");
				//mail
				mail.setMailContent("");
				
				mailService.sendEmail(mail);
			}).start();
		}
	}
}
