package com.se.dungcuthethao.mail;

import java.time.LocalDate;
import java.util.Iterator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.se.dungcuthethao.entity.ChiTietHoaDon;
import com.se.dungcuthethao.entity.HoaDon;
import com.se.dungcuthethao.entity.KhachHang;
import com.se.dungcuthethao.entity.enumEntity.ThanhToanEnum;
import com.se.dungcuthethao.service.HoaDonService;

@Aspect
@Component
public class MailAspect {
	
	@Autowired
	private HoaDonService hoaDonService;
	
	@Autowired
	private MailService mailService;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	@AfterReturning(pointcut = "execution(* com.se.dungcuthethao.controller.HoaDonController.update(..))", returning = "responseEntity")
	public void afterOrder(JoinPoint joinPoint, ResponseEntity<?> responseEntity) {
		Long id = (Long) joinPoint.getArgs()[0];
		HoaDon hoaDon = hoaDonService.findById(id);
		KhachHang khachHang = hoaDon.getKhachHang();
		float ship = 0;
		if(hoaDon.getThanhToan() == ThanhToanEnum.COD) {
			ship = 30000;
		}
		String trangThai = "";
		switch (hoaDon.getTrangThai().toString()) {
		case "PENDING":
			trangThai = "đang chờ xác nhận";
			break;
		case "PROCESSING":
			trangThai = "đang được giao đến";
			break;
		case "COMPLETED":
			trangThai = "đã giao thành công";
			break;
		case "CANCELED":
			trangThai = "đã hủy";
			break;
		default:
			break;
		}
		
		StringBuilder mailContent = new StringBuilder("<div class=\"header\" style=\"height: 20px;\"></div>\r\n"
				+ "    <div class=\"mail_header\" style=\"min-width:360px\">\r\n"
				+ "        <div>\r\n"
				+ "            <h2>"+khachHang.getTen()+" thân mến,</h2>\r\n"
				+ "        </div>\r\n"
				+ "        <div>\r\n"
				+ "            <p>Đơn hàng của anh/chị đã được đặt thành công vào <b>"+LocalDate.now()+"</b></p>\r\n"
				+ "        </div>\r\n"
				+ "        <div>\r\n"
				+ "            <p>Trạng thái đơn hàng "+trangThai+"</p>\r\n"
				+ "        </div>\r\n"
				+ "    </div>\r\n"
				+ "    <div class=\"mail_body\">\r\n"
				+ "        <div>\r\n"
				+ "            <p><b>Thông tin khách hàng</b></p>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"customer_info\">\r\n"
				+ "            <div><span>Tên Khách Hàng: "+khachHang.getTen()+"</span><br>\r\n"
				+ "                Địa chỉ giao hàng: "+hoaDon.getDiaChiGiaoHang()+"<br>\r\n"
				+ "                Điện thoại: "+khachHang.getSdt()+"<br>\r\n"
				+ "                Email: <a href=\"mailto:"+khachHang.getEmail()+"\" target=\"_blank\">"+khachHang.getEmail()+"</a>\r\n"
				+ "            </div>\r\n"
				+ "        </div>");
		// load danh sách chi tiết hóa đơn
		int i = 1;
		for (Iterator<ChiTietHoaDon> iterator = hoaDon.getChiTietHoaDons().iterator(); iterator.hasNext();) {
			ChiTietHoaDon chiTietHoaDon = iterator.next();
			String item = "<div class=\"shipmentIndex\">\r\n"
					+ "            <p><b>KIỆN HÀNG #"+i+"</b></p>\r\n"
					+ "        </div>\r\n"
					+ "\r\n"
					+ "        <div class=\"orderdetails_info\">\r\n"
					+ "            <div class=\"orderdetails_image\">\r\n"
					+ "                <img src=\""+chiTietHoaDon.getSanPham().getImages()+"\" width=\"240\" height=\"300\" />\r\n"
					+ "            </div>\r\n"
					+ "            <div class=\"orderdetails_name\">\r\n"
					+ "                "+chiTietHoaDon.getSanPham().getTen()+"\r\n"
					+ "            </div>\r\n"
					+ "            <div class=\"orderdetails_price\">\r\n"
					+ "                VND "+chiTietHoaDon.tongHoaDon()+"\r\n"
					+ "            </div>\r\n"
					+ "            <div class=\"orderdetails_quantity\">\r\n"
					+ "                Số lượng: "+chiTietHoaDon.getSoLuong()+"\r\n"
					+ "            </div>\r\n"
					+ "        </div>";
			mailContent.append(item);
			i++;
		}
		// mail footer
		double thanhTien = hoaDon.getTong() + ship;
		mailContent.append("<div class=\"mail_footer\">\r\n"
				+ "        <div>\r\n"
				+ "            <div>\r\n"
				+ "                <p>Giao hàng Tiêu chuẩn</p>\r\n"
				+ "            </div>\r\n"
				+ "            <div class=\"order_paymentMethod\">\r\n"
				+ "                <p>Thanh toán khi nhận hàng</p>\r\n"
				+ "            </div>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"price_info\">\r\n"
				+ "            <table class=\"checkout_amount\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td valign=\"top\">Thành tiền: </td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">VND</td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">"+hoaDon.getTong()+"</td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td valign=\"top\">Giảm giá: </td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">VND</td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">0</td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td valign=\"top\">Phí vận chuyển: </td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">VND</td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">"+ship+"</td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr class=\"total\">\r\n"
				+ "                        <td valign=\"top\">Tổng cộng: </td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">VND</td>\r\n"
				+ "                        <td valign=\"top\" align=\"right\">"+thanhTien+"</td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </tbody>\r\n"
				+ "            </table>\r\n"
				+ "        </div>\r\n"
				+ "	       <div>\r\n"
				+ "            <p>Nếu Anh/chị có bất kỳ câu hỏi nào, xin liên hệ với chúng tôi tại: <a href=\"mailto:"+mailFrom+"\" ></a>"+mailFrom+"</p>\r\n"
				+ "            <p>Xin chân thành cảm ơn anh/chị đã đặt hàng tại V-A-L shop</p>\r\n"
				+ "            <p>Trân trọng,</p>\r\n"
				+ "            <p><strong>Quản lý cửa hàng Dụng cụ thể thao V-A-L</strong></p>\r\n"
				+ "        </div>"
				+ "    </div>\r\n"
				+ "    <div class=\"footer\" style=\"height: 30px;\"></div>");
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			new Thread( () -> {
				Mail mail = new Mail();
				mail.setMailFrom(mailFrom);
				mail.setMailTo(khachHang.getEmail());
				mail.setMailSubject("Đặt hàng thành công tại V-A-L shop");
				mail.setMailContent(mailContent.toString());
				
				mailService.sendEmail(mail);
			}).start();
		}
	}
}
