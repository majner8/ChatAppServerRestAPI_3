package chatAPP_DTO.User;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

/**This class can be used just as Input */
public class UserAuthPasswordDTOInput{
	@NotNull
	@Pattern(regexp = "^(?=.*[0-9])(?=.*\\p{Lu})(?=.*\\p{Ll}).{3,15}$")
	private String password;


	public static class UserAuthPasswordDTOOutPut extends UserAuthPasswordDTOInput{
		@PastOrPresent
		@NotNull
		private LocalDateTime lastPasswordChange;

		public LocalDateTime getLastPasswordChange() {
			return lastPasswordChange;
		}

		public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
			this.lastPasswordChange = lastPasswordChange;
		}

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




}

