package ChatAPP_test.Authorization;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_DTO.User.UserDTO.UserAuthorizationDTO;

@Component
@Profile("test")
public class jwtTokenTestAuthorizationToken {


	@Autowired
    private WebTestClient webTestClient;
	 @Autowired
	private SecurityProperties securityProperties;
	
	 
	 private UserAuthorizationDTO user;

    /** Method returns all headers needed to be processed through AuthorizationFilter */
	public Map<String,String> getAuthorizationHeaders(){
		
	}
	
	private Map<String,String>getUserTokenHeader(Map<String,String>deviceHeader){
		this.webTestClient
				.post()
				.uri("/authorization/register")
				.bodyValue(user)
				.headers((headers)->{
					headers.addAll(headers);
				})
				.exchange();
	}
	
	private Map<String,String> getDeviceIDHeader(){
		//calling request first time, to fill empty database with ID
		this.webTestClient
					.get()
					.uri("/authorization/generateDeviceID")			
					.exchange()
					.expectBody(String.class)
					.consumeWith((S)->{
						deviceToken=S.getResponseBody();
					});
		HashMap<String,String> map=new HashMap<String,String>();
		map.put(this.securityProperties.getTokenDeviceIdHeaderName(), deviceToken);
		return map;
	}
}
