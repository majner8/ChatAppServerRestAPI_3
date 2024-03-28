package chatAPP_DTO.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserProfileDTO{
	@NotNull
	@NotEmpty
	private String SerName;
	@NotNull
	@NotEmpty
	private String lastName;
	@Max(20)
	@NotNull	
	@NotEmpty
	private String nickName;
	
	
	
	public String getSerName() {
		return SerName;
	}


	public void setSerName(String serName) {
		SerName = serName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}