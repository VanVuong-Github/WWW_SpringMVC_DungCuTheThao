package com.se.dungcuthethao.service;

import java.util.List;

import com.se.dungcuthethao.entity.HoaDon;

public interface HoaDonService {
	public List<HoaDon> findAdd();

	public HoaDon findById(Long id);
	
	public HoaDon findCart(Long id);

	public void save(HoaDon hoaDon);
	
	public void update(HoaDon hoaDon);

	public void deleteById(Long id);
	
	public List<HoaDon> getHoaDonsByThanhToan(String thanhToan);
	
	public List<HoaDon> getHoaDonsByTrangThai(String trangThai);

}