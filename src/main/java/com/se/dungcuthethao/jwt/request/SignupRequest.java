package com.se.dungcuthethao.jwt.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class SignupRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Username không được để trống")
	private String username;

	@NotBlank(message = "Mật khẩu không được để trống")
	private String password;

	public SignupRequest(@NotBlank(message = "Username không được để trống") String username,
			@NotBlank(message = "Mật khẩu không được để trống") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public SignupRequest() {
		super();
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

	@Override
	public String toString() {
		return "SignupRequest [username=" + username + ", password=" + password + "]";
	}
	
	
}
