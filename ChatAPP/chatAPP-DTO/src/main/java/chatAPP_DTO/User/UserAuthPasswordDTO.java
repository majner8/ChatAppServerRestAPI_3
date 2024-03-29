package chatAPP_DTO.User;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

public class UserAuthPasswordDTO{
	@NotNull
	@Pattern(regexp = "^(?=.*[0-9])(?=.*\\p{Lu})(?=.*\\p{Ll}).{3,15}$")
	private String password;
	
	@PastOrPresent
	@NotNull
	private LocalDateTime lastPasswordChange;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}
	
	
}

