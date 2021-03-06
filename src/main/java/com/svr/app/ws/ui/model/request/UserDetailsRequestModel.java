package com.svr.app.ws.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsRequestModel {
	@NotNull(message="First name cannot be missing or empty")
	@Size(min=2, message="First name must be a minimum of 2 characters")
	private String firstName;
	@NotNull(message="Last name cannot be missing or empty")
	private String lastName;
	@NotNull
	@Email
	private String email;
	@NotNull(message="Password is a required field")
	@Size(min=8, max=32, message="Password must be equal to or greater than 8 characters and less than 32")
	private String password;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
