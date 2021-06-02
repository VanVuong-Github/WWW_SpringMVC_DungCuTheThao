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

import com.se.dungcuthethao.entity.SanPham;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.SanPhamService;

/**
 * Controller cho các request liên quan đến sản phẩm
 * 
 * @author Lại Văn Vượng
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SanPhamController {

	@Autowired
	private SanPhamService sanPhamService;

	@Autowired
	public SanPhamController(SanPhamService sanPhamService) {
		super();
		this.sanPhamService = sanPhamService;
	}

	@GetMapping("/sanphams")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<SanPham>>(sanPhamService.findAdd(), HttpStatus.OK);
	}

	@GetMapping("/sanphams/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		SanPham rs = sanPhamService.findById(id);
		if (rs != null)
			return new ResponseEntity<SanPham>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm có id: " + id));
	}

	@PostMapping(value = "/sanphams", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> addSanPham(@RequestBody SanPham sanPham) {
		SanPham rs = sanPhamService.findById(sanPham.getId());
		if (rs != null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Đã tồn tại sản phẩm này !!"));
		} else {
			sanPhamService.save(sanPham);
			return new ResponseEntity<SanPham>(sanPham, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@PutMapping(value = "/sanphams/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateSanPham(@PathVariable("id") Long id, @RequestBody SanPham sanPham) {
		SanPham rs = sanPhamService.findById(id);
		if (rs != null) {
			sanPhamService.update(sanPham);
			return new ResponseEntity<SanPham>(sanPham, HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật sản phẩm không thành công"));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@DeleteMapping("/sanphams/{id}")
	public ResponseEntity<?> deleteSanPham(@PathVariable("id") Long id) {
		try {
			sanPhamService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Xóa thành công sản phẩm"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Xóa sản phẩm không thành công!!"));
		}
	}
	
	@GetMapping(value = "/sanphams/search/{ten}")
	public ResponseEntity<?> getSanPhamsByName(@PathVariable("ten") String name) {
		return new ResponseEntity<List<SanPham>>(sanPhamService.getSanPhamsByName(name), HttpStatus.OK);
	}
}
