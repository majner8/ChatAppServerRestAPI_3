package chatAPP_DTO.User;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;



	public  class UserProfileRegistrationDTO extends UserProfileDTO{
		@Past
		@NotNull
		private LocalDate userBorn;

		public LocalDate getUserBorn() {
			return userBorn;
		}

		public void setUserBorn(LocalDate userBorn) {
			this.userBorn = userBorn;
		}

	}

