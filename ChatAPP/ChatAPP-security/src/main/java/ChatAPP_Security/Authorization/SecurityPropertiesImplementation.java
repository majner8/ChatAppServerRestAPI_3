package ChatAPP_Security.Authorization;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

@Component
public class SecurityPropertiesImplementation implements SecurityProperties {
	
	@Value("")
	private String DeviceIDPasswordAlgo;
	@Value("")
	private String UserPasswordAlgo;
	@Value("")
	private Duration deviceIDTokenDuration;
	@Value("")
	private Duration UserAuthorizationTokenDuration;
	@Value("")
	private String DeviceIDAtrributeRequestName;
	@Value("")
	private String deviceIdClaimName;
	@Value("")
	private String versionClaimName;
	@Value("")
	private String isUserActiveClaimName;
	
	//have to be done in PostConc
	private Algorithm userAlgo;
	//have to be done in PostConc
	private Algorithm deviceAlgo;
	
	private final String deviceIDTokenPreflix="Device Bearer:";
	private final String userTokenPreflix ="Bearer:";
	private final String deviceIDTokenHeaderName="deviceIdHeaderName";
	private final String userTokenHeaderName="Authorization";
	private final String authority_userIsActive="user_enable";
	private final String authority_userIsDisable="user_disable";

	@PostConstruct
	private void init() {
		this.userAlgo=Algorithm.HMAC512(this.UserPasswordAlgo);
		this.deviceAlgo=Algorithm.HMAC512(this.DeviceIDPasswordAlgo);

	}



	@Override
	public Algorithm getjwtTokenDeviceIDAlgorithm() {
		return this.deviceAlgo;
	}

	@Override
	public Algorithm getjwtTokenAuthorizationUserAlgorithm() {
		// TODO Auto-generated method stub
		return this.userAlgo;
	}
	@Override
	public String getTokenDeviceIdPreflix() {
		// TODO Auto-generated method stub
		return this.deviceIDTokenPreflix;
	}
	
	@Override
	public String getTokenAuthorizationUserPreflix() {
		// TODO Auto-generated method stub
		return this.userTokenPreflix;
	}
	
	@Override
	public String getTokenDeviceIdHeaderName() {
		// TODO Auto-generated method stub
		return this.deviceIDTokenHeaderName;
	}
	@Override
	public String getTokenAuthorizationUserHederName() {
		// TODO Auto-generated method stub
		return this.userTokenHeaderName;
	}
	
	@Override
	public LocalDateTime getJwtTokenDeviceIdDuration() {
		// TODO Auto-generated method stub
		return LocalDateTime.now().plus(this.deviceIDTokenDuration);
	}

	@Override
	public LocalDateTime getJwtTokenAuthorizationUserDuration() {
		// TODO Auto-generated method stub
		return LocalDateTime.now().plus(this.UserAuthorizationTokenDuration);
	}
	@Override
	public String isUserActiveAuthorityName() {
		// TODO Auto-generated method stub
		return this.authority_userIsActive;
	}

	@Override
	public String isUserUnActiveAuthorityName() {
		// TODO Auto-generated method stub
		return this.authority_userIsDisable;
	}

	@Override
	public String getDeviceIDRequestAttributeName() {
		// TODO Auto-generated method stub
		return this.DeviceIDAtrributeRequestName;
	}

	@Override
	public String getDeviceId_TokenClaimName() {
		// TODO Auto-generated method stub
		return this.deviceIdClaimName;
	}

	@Override
	public String getVersion_TokenClaimName() {
		// TODO Auto-generated method stub
		return this.versionClaimName;
	}

	@Override
	public String getUserIsActive_TokenClaimName() {
		// TODO Auto-generated method stub
		return this.isUserActiveClaimName;
	}

	
}
