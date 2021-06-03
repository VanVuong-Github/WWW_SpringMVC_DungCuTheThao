package com.se.dungcuthethao.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.dungcuthethao.entity.KhachHang;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.KhachHangService;

/**
 * Controller cho các request liên quan đến khách hàng
 * 
 * @author Lại Văn Vượng
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class KhachHangController {
	@Autowired
	private KhachHangService khachHangService;

	@Autowired
	public KhachHangController(KhachHangService khachHangService) {
		super();
		this.khachHangService = khachHangService;
	}

	@GetMapping("/khachhangs")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<KhachHang>>(khachHangService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/khachhangs/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		KhachHang rs = khachHangService.findById(id);
		if (rs != null)
			return new ResponseEntity<KhachHang>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy khách hàng có id: " + id));
	}

	@PutMapping(value = "/khachhangs/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateKhachHang(@PathVariable("id") Long id, @RequestBody KhachHang khachHang) {
		KhachHang rs = khachHangService.findById(id);
		if (rs != null) {
			khachHangService.update(khachHang);
			return new ResponseEntity<KhachHang>(khachHang, HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật khách hàng không thành công"));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@DeleteMapping("/khachhangs/{id}")
	public ResponseEntity<?> deleteKhachHang(@PathVariable("id") Long id) {
		try {
			khachHangService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Xóa thành công khách hàng"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(
					"Xóa khách hàng không thành công. Chỉ xóa được khi khách hàng này chưa lập hóa đơn nào"));
		}
	}
	
	@GetMapping(value = "/khachhangs/search/{ten}")
	public ResponseEntity<?> getKhachHangsByName(@PathVariable("ten") String name) {
		return new ResponseEntity<List<KhachHang>>(khachHangService.getKhachHangsByName(name), HttpStatus.OK);
	}
}
