package com.se.dungcuthethao.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userName")
	private String username;

	@JsonIgnore
	@Column(name = "password")
	private String password;

//	@Column(name = "enable")
//	private boolean enable;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "TaiKhoan_role", joinColumns = @JoinColumn(name = "taiKhoanID"), inverseJoinColumns = @JoinColumn(name = "roleID"))
	private Set<Role> roles;

	@JsonIgnore
	@OneToOne(mappedBy = "taiKhoan", cascade = { CascadeType.ALL })
	private KhachHang khachHang;

//	public TaiKhoan(int id, String userName, String password, boolean enabled, Set<Role> roles, KhachHang khachHang) {
//		super();
//		this.id = id;
//		this.username = userName;
//		this.password = password;
//		this.enable = enabled;
//		this.roles = roles;
//		this.khachHang = khachHang;
//	}

	public TaiKhoan() {
	}

//	public boolean isEnable() {
//		return enable;
//	}
//
//	public void setEnable(boolean enable) {
//		this.enable = enable;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	@Override
	public String toString() {
		return "TaiKhoan [id=" + id + ", userName=" + username + ", password=" + password + ", roles=" + roles + "]";
	}

}