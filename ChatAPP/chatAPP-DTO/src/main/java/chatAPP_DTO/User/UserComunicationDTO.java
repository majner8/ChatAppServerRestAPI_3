package chatAPP_DTO.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import chatAPP_DTO.Validation.MultipleNullChechValidatorUserComunicationDTO;
@MultipleNullChechValidatorUserComunicationDTO
public class UserComunicationDTO{
	@Email
	private String email;
	@Pattern(regexp = "^[0-9]{1,3}$")
	private String phonePreflix;
	@Pattern(regexp = "^[0-9]{1,12}$")
	private String phone;
	//have to add validation, that phone and phonePreflix cannot be null,

	public String getEmail() {
		return email;
	}

	public UserComunicationDTO setEmail(String email) {
		this.email = email;
		return this;

	}

	public String getPhonePreflix() {
		return phonePreflix;
	}

	public UserComunicationDTO setPhonePreflix(String phonePreflix) {
		this.phonePreflix = phonePreflix;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public UserComunicationDTO setPhone(String phone) {
		this.phone = phone;
		return this;

	}

}


