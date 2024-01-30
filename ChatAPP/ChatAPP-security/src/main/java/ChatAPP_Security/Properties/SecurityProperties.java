package ChatAPP_Security.Properties;

import java.util.Date;

import com.auth0.jwt.algorithms.Algorithm;


public interface SecurityProperties {

	public Algorithm getjwtTokenDeviceIDAlgorithm();
	public Algorithm getjwtTokenAuthorizationUserAlgorithm();
	public String getTokenDeviceIdPreflix();
	public String getTokenAuthorizationUserPreflix();
	public String getTokenDeviceIdHeaderName();
	public String getTokenAuthorizationUserHederName();
	public Date getJwtTokenDeviceIdDuration();
	public Date getJwtTokenAuthorizationUserDuration();



	public String isUserActiveAuthorityName();
	public String isUserUnActiveAuthorityName();
	public String getDeviceIDRequestAttributeName();
	public String getDeviceId_TokenClaimName();
	public String getVersion_TokenClaimName();
	public String getUserIsActive_TokenClaimName();
}

