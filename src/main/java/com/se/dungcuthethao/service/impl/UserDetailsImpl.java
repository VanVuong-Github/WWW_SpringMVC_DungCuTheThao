package com.se.dungcuthethao.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.se.dungcuthethao.entity.TaiKhoan;

public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private TaiKhoan taiKhoan;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(TaiKhoan taiKhoan, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.taiKhoan = taiKhoan;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(TaiKhoan taiKhoan) {
		List<GrantedAuthority> authorities = taiKhoan.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toList());
		return new UserDetailsImpl(taiKhoan, authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return taiKhoan.getPassword();
	}

	@Override
	public String getUsername() {
		return taiKhoan.getUsername();
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) obj;
		return Objects.equals(taiKhoan.getId(), user.taiKhoan.getId());
	}
}
