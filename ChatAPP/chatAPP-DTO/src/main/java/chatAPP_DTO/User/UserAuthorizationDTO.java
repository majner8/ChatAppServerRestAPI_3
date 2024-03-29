package chatAPP_DTO.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UserAuthorizationDTO{
	@NotNull
	@Valid
	private UserAuthPasswordDTO password;
	@Valid
	@NotNull
	private UserComunicationDTO profile;
	
	public UserAuthPasswordDTO getPassword() {
		return password;
	}
	public void setPassword(UserAuthPasswordDTO password) {
		this.password = password;
	}
	public UserComunicationDTO getProfile() {
		return profile;
	}
	public void setProfile(UserComunicationDTO profile) {
		this.profile = profile;
	}
	
	
}

