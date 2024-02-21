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
import chatAPP_DTO.User.UserDTO.UserAuthPasswordDTO;
import chatAPP_DTO.User.UserDTO.UserAuthorizationDTO;
import chatAPP_DTO.User.UserDTO.UserComunicationDTO;
import chatAPP_DTO.User.UserDTO.UserProfileDTO;

@Component
@Profile("test")
@Scope("prototype")
public class jwtTokenTestAuthorizationToken {
    /** Method returns all headers needed to be processed through AuthorizationFilter */
	public Map<String,String> getAuthorizationHeaders(){
		return this.getFullAuthorizatedUserHeader(this.getUserTokenHeader(this.getDeviceIDHeader()));
	}
	private TokenDTO tokenDTO;

	/**Metod return all UserAuthorizationHeader, including deviceIDHeader */
	private Map<String,String>getUserTokenHeader(Map<String,String>deviceHeader){
		
		//make registration
	this.webTestClient
				.post()
				.uri("/authorization/register")
				.bodyValue(user)
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

	private Map<String,String> getDeviceIDHeader(){
		//calling request first time, to fill empty database with ID
		this.webTestClient
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
	
	private Map<String,String> getFullAuthorizatedUserHeader(Map<String,String> UserAuthorizatedHeader){
		UserProfileDTO prof=new UserProfileDTO();
		prof.setLastName("Bicak");
		prof.setSerName("Antonin");
		prof.setNickName("majner8");
	this.webTestClient
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
    private WebTestClient webTestClient;
	 @Autowired
	private SecurityProperties securityProperties;
	@SpyBean
	private DeviceIDGenerator generator;
	
	private UserAuthorizationDTO user;

	@PostConstruct
	   public void setUp() {
//	        MockitoAnnotations.openMocks(this);
		this.initRegistrationUser();
		this.initMockBean();
	    }
	private void initMockBean() {
		 // Using an Answer to delegate to the real method after the first call
	    AtomicInteger count = new AtomicInteger();
	    Mockito.when(this.generator.generateDeviceID()).thenAnswer(invocation -> {
            return "03df76fc-a253-4003-a505-56a8c8e57436"; // First call returns this
	    });
	}
	private void initRegistrationUser() {
		this.user=new UserAuthorizationDTO();
		//create fake profile and fill it with data
				UserComunicationDTO userProfile=new UserComunicationDTO();
				userProfile.setEmail("Antonin.bicak@gmail.com");
				userProfile.setPhone("5353");
				userProfile.setPhonePreflix("54");
				user.setProfile(userProfile);
				UserAuthPasswordDTO pas=new UserAuthPasswordDTO();
				pas.setPassword("dasdas");
				user.setPassword(pas);
	}

	
	
}
