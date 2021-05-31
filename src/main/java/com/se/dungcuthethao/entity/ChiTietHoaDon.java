package com.se.dungcuthethao.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ChiTietHoaDon")
public class ChiTietHoaDon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "donGia")
	private float donGia;

	@Column(name = "soLuong")
	private int soLuong;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hoaDonID")
	private HoaDon hoaDon;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name = "sanPhamID")
	private SanPham sanPham;

	public ChiTietHoaDon(Long id, float donGia, int soLuong) {
		super();
		this.id = id;
		this.donGia = donGia;
		this.soLuong = soLuong;
	}

	public ChiTietHoaDon(Long id, float donGia, int soLuong, HoaDon hoaDon, SanPham sanPham) {
		super();
		this.id = id;
		this.donGia = donGia;
		this.soLuong = soLuong;
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
	}

	public ChiTietHoaDon() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	@Override
	public String toString() {
		return "ChiTietHoaDon [id=" + id + ", donGia=" + donGia + ", soLuong=" + soLuong + "]";
	}

	public Double tongHoaDon() {
		return (double) (getSoLuong() * getDonGia());
	}
}