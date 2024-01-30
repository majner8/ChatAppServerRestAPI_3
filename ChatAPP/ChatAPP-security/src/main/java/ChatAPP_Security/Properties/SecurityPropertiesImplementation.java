package ChatAPP_Security.Properties;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Component
public class SecurityPropertiesImplementation implements SecurityProperties {
	

	public SecurityPropertiesImplementation() {
	}
	@Value("${security.token.password.algorithm.deviceid}")
    private String securityTokenPasswordAlgorithDeviceid;

    @Value("${security.token.password.algorithm.user}")
    private String securityTokenPasswordAlgorithUser;

    @Value("${security.token.duration.user.authorization}")
    private Duration securityTokenDurationUserAuthorization;

    @Value("${security.token.duration.deviceid}")
    private Duration securityTokenDurationDeviceid;

    @Value("${security.claim.name.deviceid}")
    private String securityClaimNameDeviceid;

    @Value("${security.claim.name.version}")
    private String securityClaimNameVersion;

    @Value("${security.claim.name.isuseractive}")
    private String securityClaimNameIsuseractive;

    @Value("${security.token.prefix.user}")
    private String securityTokenPreflixUser;

    @Value("${security.token.prefix.deviceid}")
    private String securityTokenPreflixDeviceid;

    @Value("${security.request.header.name.token.user}")
    private String securityRequestHeaderNameTokenUser;

    @Value("${security.request.header.name.token.deviceid}")
    private String securityRequestHeaderNameTokenDeviceid;
	//have to be done in PostConc
	private Algorithm userAlgo;
	//have to be done in PostConc
	private Algorithm deviceAlgo;
	
	private final String securityRequestAttributeDeviceid="device_id";
	private final String userIsActiveAuthorityName="user_enable";
	private final String userIsUnActiveAuthorityName="user_disable";
	/*
	 * private final String deviceIDTokenPreflix="Device Bearer:"; private final
	 * String userTokenPreflix ="Bearer:"; private final String
	 * deviceIDTokenHeaderName="deviceIdHeaderName"; private final String
	 * userTokenHeaderName="Authorization"; private final String
	 * authority_userIsActive="user_enable"; private final String
	 * authority_userIsDisable="user_disable";
	 */
	@PostConstruct
	private void init() {
		this.userAlgo=Algorithm.HMAC512(this.securityTokenPasswordAlgorithUser);
		this.deviceAlgo=Algorithm.HMAC512(this.securityTokenPasswordAlgorithDeviceid);
		if(Log4j2.log.isDebugEnabled()) {
			Log4j2.log.debug(Log4j2.MarkerLog.Security.getMarker(),String.format("Security token User duration is : %s"
					+System.lineSeparator()+"Recomended to chech it with value in properties file"
					, this.securityTokenDurationUserAuthorization.toString()));

		}
		
		Log4j2.log.info(Log4j2.MarkerLog.Security.getMarker(),"SecurityProperties was loaded properly");

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
		return this.securityTokenPreflixDeviceid;
	}
	
	@Override
	public String getTokenAuthorizationUserPreflix() {
		// TODO Auto-generated method stub
		return this.securityTokenPreflixUser;
	}
	
	@Override
	public String getTokenDeviceIdHeaderName() {
		// TODO Auto-generated method stub
		return this.securityRequestHeaderNameTokenDeviceid;
	}
	@Override
	public String getTokenAuthorizationUserHederName() {
		// TODO Auto-generated method stub
		return this.securityRequestHeaderNameTokenUser;
	}
	
	@Override
	public Date getJwtTokenDeviceIdDuration() {
		// TODO Auto-generated method stub
		return Date.from(ZonedDateTime.now().plus(this.securityTokenDurationDeviceid).toInstant());
	}

	@Override
	public Date getJwtTokenAuthorizationUserDuration() {
		// TODO Auto-generated method stub
		return Date.from(ZonedDateTime.now().plus(this.securityTokenDurationUserAuthorization).toInstant());
	}
	@Override
	public String isUserActiveAuthorityName() {
		// TODO Auto-generated method stub
		return this.userIsActiveAuthorityName;
	}

	@Override
	public String isUserUnActiveAuthorityName() {
		// TODO Auto-generated method stub
		return this.userIsUnActiveAuthorityName;
	}

	@Override
	public String getDeviceIDRequestAttributeName() {
		// TODO Auto-generated method stub
		return this.securityRequestAttributeDeviceid;
	}

	@Override
	public String getDeviceId_TokenClaimName() {
		// TODO Auto-generated method stub
		return this.securityClaimNameDeviceid;
	}

	@Override
	public String getVersion_TokenClaimName() {
		// TODO Auto-generated method stub
		return this.securityClaimNameVersion;
	}

	@Override
	public String getUserIsActive_TokenClaimName() {
		// TODO Auto-generated method stub
		return this.securityClaimNameIsuseractive;
	}

	
}
