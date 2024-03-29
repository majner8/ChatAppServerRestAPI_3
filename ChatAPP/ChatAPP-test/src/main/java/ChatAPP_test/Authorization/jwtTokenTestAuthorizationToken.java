package ChatAPP_test.Authorization;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import ChatAPP_Security.Authorization.DeviceID.DeviceIDGenerator;
import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_DTO.Authorization.TokenDTO;

@Component
@Profile("test")
public class jwtTokenTestAuthorizationToken {
    /** Method returns all headers needed to be processed through AuthorizationFilter */
	public Map<String,String> getAuthorizationHeaders(WebTestClient webTestClient){
		synchronized(this.integer) {
		
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
		HashMap<String,String> map=new HashMap<String,String>();
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
				userProfile.setEmail(String.format("Antonin.%dbicak@gmail.com", this.integer.incrementAndGet()));
				userProfile.setPhone(String.valueOf(5353+this.integer.incrementAndGet()));
				userProfile.setPhonePreflix(String.valueOf(54+this.integer.incrementAndGet()));
				user.setProfile(userProfile);
				UserAuthPasswordDTO pas=new UserAuthPasswordDTO();
				pas.setPassword("dasdas"+String.valueOf(this.integer.incrementAndGet()));
				user.setPassword(pas);
				return user;
	}

	
	
}
