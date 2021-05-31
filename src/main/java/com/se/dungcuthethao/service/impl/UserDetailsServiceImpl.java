package com.se.dungcuthethao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.dungcuthethao.entity.TaiKhoan;
import com.se.dungcuthethao.service.TaiKhoanService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private TaiKhoanService taiKhoanService;

	@Override
	@Transactional
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		TaiKhoan taiKhoan = taiKhoanService.findByUsername(username);
		if (taiKhoan == null)
			throw new UsernameNotFoundException("Không tồn tại tại khoản " + username);
		return UserDetailsImpl.build(taiKhoan);
	}

}
