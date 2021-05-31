package com.se.dungcuthethao.service;

import java.util.List;

import com.se.dungcuthethao.entity.ChiTietHoaDon;

public interface ChiTietHoaDonService {
	public List<ChiTietHoaDon> findAdd();

	public ChiTietHoaDon findById(Long id);

	public void save(ChiTietHoaDon chiTietHoaDon);
	
	public void update(ChiTietHoaDon chiTietHoaDon);

	public void deleteById(Long id);
}
