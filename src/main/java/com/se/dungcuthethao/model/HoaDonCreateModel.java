package com.se.dungcuthethao.model;

import java.time.LocalDate;
import java.util.List;

import com.se.dungcuthethao.entity.enumEntity.ThanhToanEnum;

public class HoaDonCreateModel {
	private LocalDate ngayDatHang;	
	private String diaChiGiaoHang;	
	private List<ChiTietHoaDonModel> chiTietHoaDons;
	private ThanhToanEnum thanhToan;
	
	public LocalDate getNgayDatHang() {
		return ngayDatHang;
	}
	public void setNgayDatHang(LocalDate ngayDatHang) {
		this.ngayDatHang = ngayDatHang;
	}
	public String getDiaChiGiaoHang() {
		return diaChiGiaoHang;
	}
	public void setDiaChiGiaoHang(String diaChiGiaoHang) {
		this.diaChiGiaoHang = diaChiGiaoHang;
	}
	public List<ChiTietHoaDonModel> getChiTietHoaDons() {
		return chiTietHoaDons;
	}
	public void setChiTietHoaDons(List<ChiTietHoaDonModel> chiTietHoaDons) {
		this.chiTietHoaDons = chiTietHoaDons;
	}
	public ThanhToanEnum getThanhToan() {
		return thanhToan;
	}
	public void setThanhToan(ThanhToanEnum thanhToan) {
		this.thanhToan = thanhToan;
	}	
}
