package com.se.dungcuthethao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NhaCungCap")
public class NhaCungCap implements Serializable {

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

	@Column(name = "diaChi")
	private String diaChi;

	@Column(name = "sdt")
	private String sdt;

	@Column(name = "email")
	private String email;

	public NhaCungCap(Long id, String ten, String diaChi, String sdt, String email) {
		super();
		this.id = id;
		this.ten = ten;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.email = email;
	}

	public NhaCungCap() {
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

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "NhaCungCap [id=" + id + ", ten=" + ten + ", diaChi=" + diaChi + ", sdt=" + sdt + ", email=" + email
				+ "]";
	}

}