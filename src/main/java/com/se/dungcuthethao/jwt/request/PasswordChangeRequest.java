package com.se.dungcuthethao.jwt.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class PasswordChangeRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Tài khoản không được để trống")
	private String username;

	@NotBlank(message = "Mật khẩu cũ không được để trống")
	private String oldPassword;

	@NotBlank(message = "Mật khẩu mới không được để trống")
	private String newPassword;

	

	public PasswordChangeRequest(@NotBlank(message = "Tài khoản không được để trống") String username,
			@NotBlank(message = "Mật khẩu cũ không được để trống") String oldPassword,
			@NotBlank(message = "Mật khẩu mới không được để trống") String newPassword) {
		super();
		this.username = username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public PasswordChangeRequest() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
