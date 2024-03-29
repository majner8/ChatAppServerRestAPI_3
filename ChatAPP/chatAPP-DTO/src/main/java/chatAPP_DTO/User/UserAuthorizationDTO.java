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
	public UserAuthorizationDTO setPassword(UserAuthPasswordDTO password) {
		this.password = password;
		return this;
	}
	public UserComunicationDTO getProfile() {
		return profile;
	}
	public UserAuthorizationDTO setProfile(UserComunicationDTO profile) {
		this.profile = profile;
		return this;

	}
	
	
}

