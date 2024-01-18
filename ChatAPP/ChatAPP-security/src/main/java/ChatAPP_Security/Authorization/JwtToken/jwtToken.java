package ChatAPP_Security.Authorization.JwtToken;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import ChatAPP_Security.Authorization.SecurityProperties;
import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_database.User.UserEntity;
import io.jsonwebtoken.UnsupportedJwtException;

public interface jwtToken {
	public static final String deviceIDToken_claimName="";
	public static final String version_claimName="";
	public static final String isUserActive_claimName="";


public interface jwtTokenGenerator {


	
	public TokenDTO generateAuthorizationToken(
			String deviceID,
			UserEntity userEntity);
	
	/**Metod generate sign device token.
	 * @return sign token, with preflix
	 *  */
	public String generateDeviceToken(
			String deviceID);
		
	
	
	@Component
	public final static class jwtTokengeneratorClass implements jwtToken.jwtTokenGenerator{
		@Autowired
		private SecurityProperties securityProperties;


		public TokenDTO generateAuthorizationToken(
			
				String deviceID,
				UserEntity userEntity) {
			
			Calendar validUntil=this.securityProperties.getJwtTokenAuthorizationUserDuration();
			JWTCreator.Builder jwtBuilder= 
					JWT.create()
					.withSubject(String.valueOf(userEntity.getUserId()))
					.withIssuedAt(new Date())
					.withClaim(jwtToken.deviceIDToken_claimName, deviceID)
					.withClaim(jwtToken.version_claimName,userEntity.getVersion())
					.withClaim(jwtToken.isUserActive_claimName, userEntity.isUserActive())
					.withExpiresAt(validUntil.getTime());

			
			String jwtToken=jwtBuilder		
					.sign(this.securityProperties.getjwtTokenAuthorizationUserAlgorithm());

			TokenDTO token=new TokenDTO();
			token.setUserActive(userEntity.isUserActive());
			token.setValidUntil(validUntil.getTime());
			token.setToken(jwtToken);
			return token;
		}
		
		public String generateDeviceToken(
				String deviceID) {
			Calendar validUntil=this.securityProperties.getJwtTokenDeviceIdDuration();

			return JWT.create()
					.withSubject(deviceID)
					.withIssuedAt(new Date())
					.withExpiresAt(validUntil.getTime())
					.sign(this.securityProperties.getjwtTokenDeviceIDAlgorithm());
		
		}
		
		
	}

}



public interface jwtTokenValidator {

	public String jwtTokenDeviceIDTokenValidator(HttpServletRequest request);
	
	public AuthorizationUserTokenValue jwtTokenAuthorizationUserTokenValidator(HttpServletRequest request);
	@Component
	public static  final class jwtTokenValidationClass implements jwtTokenValidator{
		
		
		@Autowired
		private SecurityProperties securityProperties;
		
		private DecodedJWT verifyToken(String headerName, String tokenPreflix, HttpServletRequest request
				,Algorithm tokenAlgo) {
			// TODO Auto-generated method stub
			String rawToken=request.getHeader(headerName);
			if(rawToken==null) {
				throw new UnsupportedJwtException(null);
			}
			if(!rawToken.startsWith(tokenPreflix)) {
				throw new UnsupportedJwtException(null);
			}
			rawToken=rawToken.replaceFirst(tokenPreflix, "");
			JWT.require(tokenAlgo)
			.build()
			.verify(rawToken);				
			return JWT.decode(rawToken);


		}


		@Override
		public String jwtTokenDeviceIDTokenValidator(HttpServletRequest request) {
			// TODO Auto-generated method stub
			DecodedJWT x= this.verifyToken(this.securityProperties.getTokenDeviceIdHeaderName(), 
					this.securityProperties.getTokenDeviceIdPreflix(), request, 
					this.securityProperties.getjwtTokenDeviceIDAlgorithm());
			return x.getSubject();
		}

		@Override
		public AuthorizationUserTokenValue jwtTokenAuthorizationUserTokenValidator(HttpServletRequest request) {
			// TODO Auto-generated method stub
			DecodedJWT x= this.verifyToken(this.securityProperties.getTokenAuthorizationUserHederName(), 
					this.securityProperties.getTokenAuthorizationUserPreflix(), request, 
					this.securityProperties.getjwtTokenAuthorizationUserAlgorithm());
			return new authorizationTokenValue(x);
		}



		public static class authorizationTokenValue implements AuthorizationUserTokenValue{

			private long userID;
			private String deviceID;
			private long version;
			private boolean userEnable;
			private UserEntity entity;
			public authorizationTokenValue(DecodedJWT token) {
				this.userID=Long.valueOf(token.getSubject());
				this.deviceID=token.getClaim(jwtToken.deviceIDToken_claimName).asString();
				this.version=token.getClaim(jwtToken.version_claimName).asLong();
				this.userEnable=token.getClaim(jwtToken.isUserActive_claimName).asBoolean();
			}
			@Override
			public long getUserID() {
				// TODO Auto-generated method stub
				return this.userID;
			}

			@Override
			public String getDeviceID() {
				// TODO Auto-generated method stub
				return this.deviceID;
			}

			@Override
			public long getDatabaseVersion() {
				// TODO Auto-generated method stub
				return this.version;
			}

			@Override
			public boolean isUserEnable() {
				// TODO Auto-generated method stub
				return this.userEnable;
			}
			

		}
		
		
		
	}
	

}


}
