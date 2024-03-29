package chatAPP_DTO.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserProfileDTO{
	@NotNull
	@NotEmpty
	private String SerName;
	@NotNull
	@NotEmpty
	private String lastName;
	@Size(max=20)
	@NotNull	
	@NotEmpty
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