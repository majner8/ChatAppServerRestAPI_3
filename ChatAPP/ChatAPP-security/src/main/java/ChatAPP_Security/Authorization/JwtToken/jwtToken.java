package ChatAPP_Security.Authorization.JwtToken;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_database.User.UserEntity;
import io.jsonwebtoken.UnsupportedJwtException;

public interface jwtToken {


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


		@Override
		public TokenDTO generateAuthorizationToken(

				String deviceID,
				UserEntity userEntity) {

			Date validUntil=this.securityProperties.getJwtTokenAuthorizationUserDuration();
			JWTCreator.Builder jwtBuilder=
					JWT.create()
					.withSubject(String.valueOf(userEntity.getUserId()))
					.withIssuedAt(new Date())
					.withClaim(securityProperties.getDeviceId_TokenClaimName(), deviceID)
					.withClaim(securityProperties.getVersion_TokenClaimName(),userEntity.getVersion())
					.withClaim(securityProperties.getUserIsActive_TokenClaimName(), userEntity.isUserActive())
					.withExpiresAt(validUntil);


			String jwtToken=jwtBuilder
					.sign(this.securityProperties.getjwtTokenAuthorizationUserAlgorithm());
			jwtToken=this.securityProperties.getTokenAuthorizationUserPreflix()+jwtToken;
			TokenDTO token=new TokenDTO();
			token.setUserActive(userEntity.isUserActive());
			token.setValidUntil(validUntil);
			token.setToken(jwtToken);
			return token;
		}

		@Override
		public String generateDeviceToken(
				String deviceID) {
			Date validUntil=this.securityProperties.getJwtTokenDeviceIdDuration();

			return this.securityProperties.getTokenDeviceIdPreflix()+JWT.create()
					.withSubject(deviceID)
					.withIssuedAt(new Date())
					.withExpiresAt(validUntil)
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
			if(Log4j2.log.isTraceEnabled()) {
				Log4j2.log.trace(Log4j2.MarkerLog.Security.getMarker(),
						String.format("I am verifyToken, TokenPreflix: %s %s receivedToken %s",
								tokenPreflix,System.lineSeparator(),rawToken));

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

			if(x.getSubject()==null) {
				throw new UnsupportedJwtException(null);
			}
			return x.getSubject();

		}

		@Override
		public AuthorizationUserTokenValue jwtTokenAuthorizationUserTokenValidator(HttpServletRequest request) {
			// TODO Auto-generated method stub
			DecodedJWT x= this.verifyToken(this.securityProperties.getTokenAuthorizationUserHederName(),
					this.securityProperties.getTokenAuthorizationUserPreflix(), request,
					this.securityProperties.getjwtTokenAuthorizationUserAlgorithm());
			return new authorizationTokenValue(x,this.securityProperties);
		}



		public static class authorizationTokenValue implements AuthorizationUserTokenValue{

			private long userID;
			private String deviceID;
			private long version;
			private boolean userEnable;
			private UserEntity entity;
			public authorizationTokenValue(DecodedJWT token,SecurityProperties securityProperties) {
				this.userID=Long.valueOf(token.getSubject());
				this.deviceID=token.getClaim(securityProperties.getDeviceId_TokenClaimName()).asString();
				this.version=token.getClaim(securityProperties.getVersion_TokenClaimName()).asLong();
				this.userEnable=token.getClaim(securityProperties.getUserIsActive_TokenClaimName()).asBoolean();
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
