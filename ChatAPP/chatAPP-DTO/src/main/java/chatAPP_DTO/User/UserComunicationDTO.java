package chatAPP_DTO.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserComunicationDTO{
	@Email
	@NotNull
	private String email;
	@Size(max=3)
	private String phonePreflix;
	@Size(max=12)
	private String phone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonePreflix() {
		return phonePreflix;
	}

	public void setPhonePreflix(String phonePreflix) {
		this.phonePreflix = phonePreflix;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}


