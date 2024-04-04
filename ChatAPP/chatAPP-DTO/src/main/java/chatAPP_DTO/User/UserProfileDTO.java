package chatAPP_DTO.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserProfileDTO{

	@NotEmpty
	@Pattern(regexp = "^[^0-9]*$")
	private String SerName;
	@NotEmpty
	@Pattern(regexp = "^[^0-9]*$")
	private String lastName;
	@NotEmpty
	@Pattern(regexp = "^[^0-9]{0,20}$")
	private String nickName;
	public String getSerName() {
		return SerName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getNickName() {
		return nickName;
	}
	public UserProfileDTO setSerName(String serName) {
		SerName = serName;
		return this;
	}
	public UserProfileDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;

	}
	public UserProfileDTO setNickName(String nickName) {
		this.nickName = nickName;
		return this;

	}



}