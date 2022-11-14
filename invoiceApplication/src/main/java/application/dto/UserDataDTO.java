package application.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserDataDTO {

	@ApiModelProperty(position = 2)
	private String companyName;
//	@ApiModelProperty(position = 3)
//	private String email;
	@ApiModelProperty(position = 4)
	private String address;
	@ApiModelProperty(position = 5)
	private String phoneNumber;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

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

}
