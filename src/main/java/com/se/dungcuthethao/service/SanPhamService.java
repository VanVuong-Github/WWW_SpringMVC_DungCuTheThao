package com.se.dungcuthethao.service;

import java.util.List;

import com.se.dungcuthethao.entity.SanPham;

public interface SanPhamService {
	public List<SanPham> findAdd();
	public SanPham findById(Long id);
	public void save(SanPham sanPham);
	public void update(SanPham sanPham);
	public void deleteById(Long id);
	public List<SanPham> getSanPhamsByName(String name);
}
