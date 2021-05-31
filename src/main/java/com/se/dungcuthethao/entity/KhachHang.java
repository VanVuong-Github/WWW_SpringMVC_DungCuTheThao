package com.se.dungcuthethao.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "KhachHang")
public class KhachHang implements Serializable {
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

	@Column(name = "email")
	private String email;

	@Column(name = "sdt")
	private String sdt;

	@Column(name = "loai")
	private String loai;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "taiKhoanID")
	private TaiKhoan taiKhoan;

	public KhachHang(TaiKhoan taiKhoan) {
		super();
		this.ten = "Không xác định";
		this.email = "Chưa có email";
		this.sdt = "Chưa có số điện thoại";
		this.loai = "cơ bản";
		this.taiKhoan = taiKhoan;
	}

	public KhachHang(Long id, String ten, String email, String sdt, String loai) {
		super();
		this.id = id;
		this.ten = ten;
		this.email = email;
		this.sdt = sdt;
		this.loai = loai;
	}

	public KhachHang() {
		super();
		this.ten = "Không xác định";
		this.email = "Chưa có email";
		this.sdt = "Chưa có số điện thoại";
		this.loai = "cơ bản";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	@Override
	public String toString() {
		return "KhachHang [id=" + id + ", ten=" + ten + ", email=" + email + ", sdt=" + sdt + ", loai=" + loai + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}