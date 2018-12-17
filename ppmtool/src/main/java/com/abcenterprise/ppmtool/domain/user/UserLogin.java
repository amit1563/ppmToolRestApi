package com.abcenterprise.ppmtool.domain.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLogin  {
	
	@NotEmpty
	@Size(min=4, max=20)
	private String username;
	
	@NotEmpty
	@Size(min=4, max=20)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
