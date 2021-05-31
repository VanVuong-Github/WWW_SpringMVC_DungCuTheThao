package com.se.dungcuthethao.jwt.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.se.dungcuthethao.entity.KhachHang;
import com.se.dungcuthethao.entity.TaiKhoan;

public class JwtResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private KhachHang khachHang;
	
	@JsonUnwrapped
	private TaiKhoan taiKhoan;
	
	private String tokenType;

	private String accessToken;

	public JwtResponse(String accessToken, KhachHang khachHang, TaiKhoan taiKhoan) {
		this.accessToken = accessToken;
		this.khachHang = khachHang;
		this.taiKhoan = taiKhoan;
		tokenType = "Sport";
	}

	public JwtResponse() {
		super();
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "JwtResponse [khachHang=" + khachHang + ", taiKhoan=" + taiKhoan + ", tokenType=" + tokenType
				+ ", accessToken=" + accessToken + "]";
	}
	
}
