package com.se.dungcuthethao.model;

import com.se.dungcuthethao.entity.SanPham;

public class ChiTietHoaDonModel {
	private float donGia;
	private int soLuong;
	private SanPham sanPham;
	
	public float getDonGia() {
		return donGia;
	}
	public void setDonGia(float donGia) {
		this.donGia = donGia;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	
}
