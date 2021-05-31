package com.se.dungcuthethao.jwt.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class PasswordChangeRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Mật khẩu cũ không được để trống")
	private String oldPassword;

	@NotBlank(message = "Mật khẩu mới không được để trống")
	private String newPassword;

	public PasswordChangeRequest(@NotBlank(message = "Mật khẩu cũ không được để trống") String oldPassword,
			@NotBlank(message = "Mật khẩu mới không được để trống") String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public PasswordChangeRequest() {
		super();
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
