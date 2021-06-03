package com.se.dungcuthethao.service;

import java.util.List;

import com.se.dungcuthethao.entity.KhachHang;

public interface KhachHangService {
	
	public List<KhachHang> findAll();

	public KhachHang findById(Long id);

	public void save(KhachHang khachHang);
	
	public void update(KhachHang khachHang);

	public void deleteById(Long id);
	
	public List<KhachHang> getKhachHangsByName(String name);
	
	public KhachHang findByTaiKhoanId(Long id);
	
}