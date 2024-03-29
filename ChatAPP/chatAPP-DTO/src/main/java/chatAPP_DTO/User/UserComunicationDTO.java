package chatAPP_DTO.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserComunicationDTO{
	@Email
	@NotNull
	private String email;
	@NotNull
	@Pattern(regexp = "^[0-9]{1,3}$")
	private String phonePreflix;
	@NotNull
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


