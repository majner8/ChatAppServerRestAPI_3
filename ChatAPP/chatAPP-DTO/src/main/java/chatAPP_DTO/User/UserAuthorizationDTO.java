package chatAPP_DTO.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class UserAuthorizationDTO{
	@NotNull
	@Valid
	private UserAuthPasswordDTOInput password;
	@Valid
	@NotNull
	private UserComunicationDTO profile;

	public UserAuthPasswordDTOInput getPassword() {
		return password;
	}
	public UserAuthorizationDTO setPassword(UserAuthPasswordDTOInput password) {
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

