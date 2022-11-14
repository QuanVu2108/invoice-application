package application.dto;

import java.util.List;

import application.domain.Role;
import io.swagger.annotations.ApiModelProperty;

public class UserResponseDTO {

	@ApiModelProperty(position = 0)
	private Long id;
	@ApiModelProperty(position = 1)
	private String username;
	@ApiModelProperty(position = 2)
	private String companyName;
	@ApiModelProperty(position = 3)
	private String email;
	@ApiModelProperty(position = 4)
	private String address;
	@ApiModelProperty(position = 5)
	private String phoneNumber;
	@ApiModelProperty(position = 6)
	List<Role> roles;

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
