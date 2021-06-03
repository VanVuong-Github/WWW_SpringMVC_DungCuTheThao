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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.dungcuthethao.entity.ChiTietHoaDon;
import com.se.dungcuthethao.entity.HoaDon;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.ChiTietHoaDonService;
import com.se.dungcuthethao.service.HoaDonService;

/**
 * Controller cho các request liên quan đến chi tiết hóa đơn
 * 
 * @author Lại Văn Vượng
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ChiTietHoaDonController {
	
	@Autowired
	private HoaDonService hoaDonService;
	
	@Autowired
	private ChiTietHoaDonService chiTietHoaDonService;

	@Autowired
	public ChiTietHoaDonController(ChiTietHoaDonService chiTietHoaDonService) {
		super();
		this.chiTietHoaDonService = chiTietHoaDonService;
	}
	
	@GetMapping("/chitiethoadons")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<ChiTietHoaDon>>(chiTietHoaDonService.findAdd(), HttpStatus.OK);
	}
	
	@GetMapping("/chitiethoadons/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		ChiTietHoaDon rs = chiTietHoaDonService.findById(id);
		if (rs != null)
			return new ResponseEntity<ChiTietHoaDon>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy chi tiết hóa đơn có id: " + id));
	}
	
	@PostMapping(value = "/chitiethoadons", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> add(@RequestBody ChiTietHoaDon chiTietHoaDon) {
		HoaDon hoaDon = hoaDonService.findById(chiTietHoaDon.getHoaDon().getId());
		ChiTietHoaDon rs = chiTietHoaDonService.findById(chiTietHoaDon.getId());
		if (rs != null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Đã tồn tại chi tiết hóa đơn này !!"));
		} else {
			ChiTietHoaDon cthd = new ChiTietHoaDon();
			cthd.setDonGia(chiTietHoaDon.getDonGia());
			cthd.setHoaDon(hoaDon);
			cthd.setSanPham(chiTietHoaDon.getSanPham());
			cthd.setSoLuong(chiTietHoaDon.getSoLuong());
			chiTietHoaDonService.save(cthd);
			return new ResponseEntity<ChiTietHoaDon>(chiTietHoaDon, HttpStatus.OK);
		}
	}
	
	@PutMapping(value = "/chitiethoadons/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ChiTietHoaDon chiTietHoaDon) {
		ChiTietHoaDon rs = chiTietHoaDonService.findById(id);
		if (rs != null) {
			chiTietHoaDon.setId(id);
			chiTietHoaDonService.update(chiTietHoaDon);
			return new ResponseEntity<ChiTietHoaDon>(chiTietHoaDon, HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật chi tiết hóa đơn không thành công"));
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@DeleteMapping("/chitiethoadons/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {
			chiTietHoaDonService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Xóa thành công chi tiết hóa đơn"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Xóa chi tiết hóa đơn không thành công!!"));
		}
	}
}
