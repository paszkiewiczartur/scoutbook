package pl.scoutbook.model;

import javax.validation.constraints.NotNull;

public class ChangePassword {
	@NotNull
	private String code;
	@NotNull
	private String password;
	
	public ChangePassword(){}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
