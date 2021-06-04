package com.se.dungcuthethao.model;

import java.time.LocalDate;
import java.util.List;

import com.se.dungcuthethao.entity.enumEntity.ThanhToanEnum;
import com.se.dungcuthethao.entity.enumEntity.TrangThaiEnum;

public class HoaDonUpdateModel {
	private LocalDate ngayDatHang;
	private ThanhToanEnum thanhToan;
	private String diaChiGiaoHang;	
	private TrangThaiEnum trangThai;
	private List<ChiTietHoaDonModel> chiTietHoaDons;
//	private KhachHang khachHang;
//	
//	public KhachHang getKhachHang() {
//		return khachHang;
//	}
//	public void setKhachHang(KhachHang khachHang) {
//		this.khachHang = khachHang;
//	}
	public LocalDate getNgayDatHang() {
		return ngayDatHang;
	}
	public void setNgayDatHang(LocalDate ngayDatHang) {
		this.ngayDatHang = ngayDatHang;
	}
	public ThanhToanEnum getThanhToan() {
		return thanhToan;
	}
	public void setThanhToan(ThanhToanEnum thanhToan) {
		this.thanhToan = thanhToan;
	}
	public String getDiaChiGiaoHang() {
		return diaChiGiaoHang;
	}
	public void setDiaChiGiaoHang(String diaChiGiaoHang) {
		this.diaChiGiaoHang = diaChiGiaoHang;
	}
	
	public TrangThaiEnum getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(TrangThaiEnum trangThai) {
		this.trangThai = trangThai;
	}
	public List<ChiTietHoaDonModel> getChiTietHoaDons() {
		return chiTietHoaDons;
	}
	public void setChiTietHoaDons(List<ChiTietHoaDonModel> chiTietHoaDons) {
		this.chiTietHoaDons = chiTietHoaDons;
	}
	
}
