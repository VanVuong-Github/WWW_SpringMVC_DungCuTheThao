package com.se.dungcuthethao.service;

import com.se.dungcuthethao.entity.TaiKhoan;

public interface TaiKhoanService {
	public TaiKhoan findByUsername(String username);
	
	public void save(TaiKhoan taiKhoan);
	
	public boolean existsByUsername(String username);

}