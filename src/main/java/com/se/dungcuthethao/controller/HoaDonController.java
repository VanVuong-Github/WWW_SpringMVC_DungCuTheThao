package com.se.dungcuthethao.controller;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.dungcuthethao.entity.ChiTietHoaDon;
import com.se.dungcuthethao.entity.HoaDon;
import com.se.dungcuthethao.entity.KhachHang;
import com.se.dungcuthethao.entity.SanPham;
import com.se.dungcuthethao.entity.TaiKhoan;
import com.se.dungcuthethao.entity.enumEntity.TrangThaiEnum;
import com.se.dungcuthethao.exception.CustomException;
import com.se.dungcuthethao.jwt.JwtUtils;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.HoaDonService;
import com.se.dungcuthethao.service.KhachHangService;
import com.se.dungcuthethao.service.SanPhamService;
import com.se.dungcuthethao.service.TaiKhoanService;

/**
 * Controller cho các request liên quan đến hóa đơn
 * 
 * @author Lại Văn Vượng
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class HoaDonController {

	@Autowired
	private SanPhamService sanPhamService;

	@Autowired
	private HoaDonService hoaDonService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private TaiKhoanService taiKhoanService;

	@Autowired
	private KhachHangService khachHangService;

	@Autowired
	public HoaDonController(HoaDonService hoaDonService) {
		super();
		this.hoaDonService = hoaDonService;
	}

	@GetMapping("/hoadons")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<HoaDon>>(hoaDonService.findAdd(), HttpStatus.OK);
	}

	@GetMapping("/hoadons/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		HoaDon rs = hoaDonService.findById(id);
		if (rs != null)
			return new ResponseEntity<HoaDon>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy hóa đơn có id: " + id));
	}

//	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(value = "/createCart")
	public ResponseEntity<?> createCart(@RequestHeader("Authorization") String token) {
		String username = jwtUtils.getUserNameFromJwtToken(token.substring(6));
		TaiKhoan taiKhoan = taiKhoanService.findByUsername(username);
		KhachHang khachHang = khachHangService.findByTaiKhoanId(taiKhoan.getId());
		HoaDon hoaDon = new HoaDon(khachHang);
		hoaDonService.save(hoaDon);
		return new ResponseEntity<HoaDon>(hoaDon, HttpStatus.OK);
	}

	/**
	 * load customer cart
	 * 
	 * @param token
	 * @return
	 */
//	@AfterReturning(pointcut = "execution(* com.se.dungcuthethao.controller.AuthController.authenticateUser(..))", returning = "responseEntity")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(value = "/loadCart")
	public ResponseEntity<?> loadCart(@RequestHeader("Authorization") String token) {
		KhachHang khachHang = null;
		TaiKhoan taiKhoan = null;
		try {
			String username = jwtUtils.getUserNameFromJwtToken(token.substring(6));
			taiKhoan = taiKhoanService.findByUsername(username);
			khachHang = khachHangService.findByTaiKhoanId(taiKhoan.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Vui lòng đăng nhập để load cart"));
		}
		HoaDon hoaDon = hoaDonService.findCart(khachHang.getId());
		if (hoaDon == null) {
			createCart(token);
			hoaDon = hoaDonService.findCart(khachHang.getId());
		}
		return new ResponseEntity<HoaDon>(hoaDon, HttpStatus.OK);
	}

	@PostMapping(value = "/hoadons", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> add(@RequestBody HoaDon hoaDon) throws CustomException {
		HoaDon newHoaDon = new HoaDon(hoaDon.getKhachHang());
		newHoaDon.setDiaChiGiaoHang(hoaDon.getDiaChiGiaoHang());
		newHoaDon.setNgayDatHang(LocalDate.now());
		newHoaDon.setThanhToan(hoaDon.getThanhToan());
		newHoaDon.setTrangThai(TrangThaiEnum.PROCESSING);
		newHoaDon.setTong(hoaDon.sumTotal());
		List<ChiTietHoaDon> orderDetails = hoaDon.getChiTietHoaDons();
		for (Iterator<?> iterator = orderDetails.iterator(); iterator.hasNext();) {
			ChiTietHoaDon detail = (ChiTietHoaDon) iterator.next();
			SanPham sanPham = sanPhamService.findById(detail.getSanPham().getId());
			if (sanPham != null) {
				detail.setSanPham(sanPham);
				detail.setHoaDon(newHoaDon);
				if (detail.getSoLuong() > sanPham.getSoLuong())
					throw new CustomException("Sản phẩm " + sanPham.getTen() + " không còn đủ số lượng");
			} else
				throw new CustomException("Không tìm thấy sản phẩm có Id= " + detail.getSanPham().getId());
		}
		newHoaDon.setChiTietHoaDons(orderDetails);
		hoaDonService.save(newHoaDon);
		return new ResponseEntity<HoaDon>(newHoaDon, HttpStatus.OK);
	}

	@PutMapping(value = "/hoadons/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody HoaDon hoaDon) throws CustomException {
		HoaDon rs = hoaDonService.findById(id);
		if (rs != null) {
			hoaDon.setId(id);
			hoaDon.setTong(hoaDon.sumTotal());
			hoaDonService.update(hoaDon);
			return new ResponseEntity<HoaDon>(hoaDon, HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật hóa đơn không thành công"));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@DeleteMapping("/hoadons/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {
			hoaDonService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Xóa thành công hóa đơn"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Xóa hóa đơn không thành công!!"));
		}
	}

	/**
	 * xuất danh sách hóa đơn có phương thức thanh toán = thanhToan
	 * 
	 * @param thanhToan
	 * @return
	 */
	@GetMapping(value = "/hoadons/sort/thanh-toan/{thanhToan}")
	public ResponseEntity<?> getHoaDonsByThanhToan(@PathVariable("thanhToan") String thanhToan) {
		return new ResponseEntity<List<HoaDon>>(hoaDonService.getHoaDonsByThanhToan(thanhToan), HttpStatus.OK);
	}

	/**
	 * xuất danh sách hóa đơn có trạng thái = trangThai
	 * 
	 * @param thanhToan
	 * @return
	 */
	@GetMapping(value = "/hoadons/sort/trang-thai/{trangThai}")
	public ResponseEntity<?> getHoaDonsByTrangThai(@PathVariable("trangThai") String trangThai) {
		return new ResponseEntity<List<HoaDon>>(hoaDonService.getHoaDonsByTrangThai(trangThai), HttpStatus.OK);
	}
}
