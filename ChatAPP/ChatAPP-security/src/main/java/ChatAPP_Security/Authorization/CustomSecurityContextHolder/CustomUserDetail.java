package ChatAPP_Security.Authorization.CustomSecurityContextHolder;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ChatAPP_Security.Authorization.JwtToken.AuthorizationUserTokenValue;


public class CustomUserDetail implements UserDetails{

	protected final long userID;
	protected final long databaseVersion;
	protected final Collection<? extends GrantedAuthority> authority;
	protected final boolean isUserEnable;
	protected final String deviceID;
	protected CustomUserDetail(AuthorizationUserTokenValue user,
			Collection<? extends GrantedAuthority> authority) {
		this.authority=authority;
		this.userID=user.getUserID();
		this.deviceID=user.getDeviceID();
		this.databaseVersion=user.getDatabaseVersion();
		this.isUserEnable=user.isUserEnable();
	}
	public static CustomUserDetail createUser(AuthorizationUserTokenValue user,
			Collection<? extends GrantedAuthority> authority) {
				return new CustomUserDetail(user,authority);
		
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authority;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.deviceID+this.userID;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.isUserEnable;
	}
	public long getUserID() {
		return userID;
	}
	public long getDatabaseVersion() {
		return databaseVersion;
	}
	public String getDeviceID() {
		return deviceID;
	}
	
	}

