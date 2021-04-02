package com.nucigent.elms.user.dto;

import javax.validation.constraints.NotEmpty;

public class ResetPwdDto {
	
	@NotEmpty
	private String token;
	@NotEmpty
	private String password;
	
	@NotEmpty
    private String confirmPassword;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	

}
