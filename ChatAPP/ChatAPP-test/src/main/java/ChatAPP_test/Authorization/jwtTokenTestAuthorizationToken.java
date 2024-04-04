package ChatAPP_test.Authorization;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import ChatAPP_Security.Authorization.DeviceID.DeviceIDGenerator;
import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_DTO.User.UserAuthPasswordDTOInput;
import chatAPP_DTO.User.UserAuthorizationDTO;
import chatAPP_DTO.User.UserComunicationDTO;
import chatAPP_DTO.User.UserProfileDTO;

@Component
@Profile("test")
public class jwtTokenTestAuthorizationToken {
    /** Method returns all headers needed to be processed through AuthorizationFilter */
	public Map<String,String> getAuthorizationHeaders(WebTestClient webTestClient){
		synchronized(jwtTokenTestAuthorizationToken.integer) {

		return this.getFullAuthorizatedUserHeader(this.getUserTokenHeader(this.getDeviceIDHeader(webTestClient),webTestClient),webTestClient);
		}
	}
	private TokenDTO tokenDTO;

	/**Metod return all UserAuthorizationHeader, including deviceIDHeader */
	private Map<String,String>getUserTokenHeader(Map<String,String>deviceHeader,WebTestClient webTestClient){

		//make registration
	webTestClient
				.post()
				.uri("/authorization/register")
				.bodyValue(this.initRegistrationUser())
				.headers(httpHeaders -> deviceHeader.forEach(httpHeaders::add))
				.exchange()

				.expectStatus().isOk()
				.expectBody(TokenDTO.class)
	.consumeWith((token)->{
		tokenDTO=token.getResponseBody();
	});

	deviceHeader.put(this.securityProperties.getTokenAuthorizationUserHederName(), tokenDTO.getToken());
	return deviceHeader;
	}
	private 		 String deviceIDToken;

	private Map<String,String> getDeviceIDHeader(WebTestClient webTestClient){
		//calling request first time, to fill empty database with ID
		webTestClient
		.get()
		.uri("/authorization/generateDeviceID")
		.exchange()
		.expectBody(String.class)
		.consumeWith((device)->{
			deviceIDToken=device.getResponseBody();

		});
		HashMap<String,String> map=new HashMap<>();
		map.put(this.securityProperties.getTokenDeviceIdHeaderName(), deviceIDToken);
		return map;
	}

	private Map<String,String> getFullAuthorizatedUserHeader(Map<String,String> UserAuthorizatedHeader,WebTestClient webTestClient){
		UserProfileDTO prof=new UserProfileDTO();
		prof.setLastName("Bicak");
		prof.setSerName("Antonin");
		prof.setNickName("majner8");
	webTestClient
				.post()
				.uri("/authorization/finishRegistration")
				.bodyValue(prof)
				.headers(httpHeaders -> UserAuthorizatedHeader.forEach(httpHeaders::add))
				.exchange()
				.expectStatus().isOk()
				.expectBody(TokenDTO.class)
				.consumeWith((token)->{
		tokenDTO=token.getResponseBody();
	});
		UserAuthorizatedHeader.remove(this.securityProperties.getTokenAuthorizationUserHederName());
		UserAuthorizatedHeader.put(this.securityProperties.getTokenAuthorizationUserHederName(), tokenDTO.getToken());
		return UserAuthorizatedHeader;
	}



	 @Autowired
	private SecurityProperties securityProperties;
	@SpyBean
	private DeviceIDGenerator generator;


	//manage creating unique user
	private static  AtomicInteger integer=new AtomicInteger();
	
	static {
		integer.set(0);
	}
	private UserAuthorizationDTO initRegistrationUser() {
		UserAuthorizationDTO user=new UserAuthorizationDTO();
		//create fake profile and fill it with data
				UserComunicationDTO userProfile=new UserComunicationDTO();
				userProfile.setEmail(String.format("Antonin.%dbicak@gmail.com", jwtTokenTestAuthorizationToken.integer.incrementAndGet()));
				userProfile.setPhone(String.valueOf(5353+jwtTokenTestAuthorizationToken.integer.incrementAndGet()));
				userProfile.setPhonePreflix(String.valueOf(54+jwtTokenTestAuthorizationToken.integer.incrementAndGet()));
				user.setProfile(userProfile);
				UserAuthPasswordDTOInput pas=new UserAuthPasswordDTOInput();
				pas.setPassword("dasdas"+String.valueOf(jwtTokenTestAuthorizationToken.integer.incrementAndGet()));
				user.setPassword(pas);
				return user;
	}



}
