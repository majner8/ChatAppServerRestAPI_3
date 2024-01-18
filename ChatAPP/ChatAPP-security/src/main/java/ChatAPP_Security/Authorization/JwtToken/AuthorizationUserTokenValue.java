package ChatAPP_Security.Authorization.JwtToken;

public  interface AuthorizationUserTokenValue{
	
	public long getUserID();
	public String getDeviceID();
	public long getDatabaseVersion();
	public boolean isUserEnable();
}