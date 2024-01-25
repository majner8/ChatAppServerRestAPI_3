package chatAPP_CommontPart.Security;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsInterface extends UserDetails {

	public long getUserID();
	public long getDatabaseVersion();
	public String getDeviceID();
}
