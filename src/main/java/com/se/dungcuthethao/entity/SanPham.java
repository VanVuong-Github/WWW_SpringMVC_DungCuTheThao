package com.se.dungcuthethao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SanPham")
public class SanPham implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ten")
	private String ten;

	@Column(name = "soLuong")
	private int soLuong;

	@Column(name = "donGia")
	private float donGia;

	@Column(name = "loai")
	private String loai;

	@Column(name = "kichCo")
	private String kichCo;

	@Column(name = "mauSac")
	private String mauSac;

	@Column(name = "xuatXu")
	private String xuatXu;

	@Column(name = "images")
	private String images;

	@ManyToOne
	@JoinColumn(name = "nhaCungCapID")
	private NhaCungCap nhaCungCap;

	public SanPham(Long id, String ten, int soLuong, float donGia, String loai, String kichCo, String mauSac,
			String xuatXu, String images, NhaCungCap nhaCungCap) {
		super();
		this.id = id;
		this.ten = ten;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.loai = loai;
		this.kichCo = kichCo;
		this.mauSac = mauSac;
		this.xuatXu = xuatXu;
		this.images = images;
		this.nhaCungCap = nhaCungCap;
	}

	public SanPham(Long id, String ten, int soLuong, float donGia, String loai, String kichCo, String mauSac,
			String xuatXu, String images) {
		super();
		this.id = id;
		this.ten = ten;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.loai = loai;
		this.kichCo = kichCo;
		this.mauSac = mauSac;
		this.xuatXu = xuatXu;
		this.images = images;
	}

	public SanPham() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public float getDonGia() {
		return donGia;
	}

	public void setDonGia(float donGia) {
		this.donGia = donGia;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getKichCo() {
		return kichCo;
	}

	public void setKichCo(String kichCo) {
		this.kichCo = kichCo;
	}

	public String getMauSac() {
		return mauSac;
	}

	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	@Override
	public String toString() {
		return "SanPham [id=" + id + ", ten=" + ten + ", soLuong=" + soLuong + ", donGia=" + donGia + ", loai=" + loai
				+ ", kichCo=" + kichCo + ", mauSac=" + mauSac + ", xuatXu=" + xuatXu + ", images=" + images + "]";
	}

}