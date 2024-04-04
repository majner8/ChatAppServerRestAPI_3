package ChatAPP.Test.Authorization;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import ChatAPP_Security.Properties.SecurityProperties;
import ChatAPP_test.Authorization.jwtTokenTestAuthorizationToken;
import chatAPP_CommontPart.Data.Util.Triple;
import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_DTO.User.UserAuthPasswordDTOInput;
import chatAPP_DTO.User.UserAuthorizationDTO;
import chatAPP_DTO.User.UserComunicationDTO;
import chatAPP_DTO.User.UserProfileDTO;

@Component
@Scope("prototype")
@Profile("chatAPP-Test")
public class CreateAuthorizationAcessTest {
	private static  AtomicInteger integer=new AtomicInteger();
	static {
		integer.set(0);
	}
	private ThreadLocal<Triple<Integer,WebTestClient,String>> threadLocal=new ThreadLocal<Triple<Integer,WebTestClient,String>>();
	@Autowired
	private WebTestClient webTestClient;
	 @Autowired
		private SecurityProperties securityProperties;
	 
	/** Method returns all headers needed to be processed through AuthorizationFilter */
		public Map<String,String> getAuthorizationHeaders(){
			this.threadLocal.remove();
			
			this.threadLocal.set(new Triple<Integer, WebTestClient, String>());
			this.threadLocal.get().setFirst(this.integer.getAndIncrement());
			this.threadLocal.get().setSecond(webTestClient);
		
			Map<String,String>headers=new HashMap<String,String>();
			this.getDeviceIDHeader(headers);
			this.getUserTokenHeader(headers);
			this.getFullAuthorizatedUserHeader(headers);
			this.threadLocal.remove();
			return headers;
		}
		
		

		private void getDeviceIDHeader(Map<String,String>deviceHeader){
			//calling request first time, to fill empty database with ID
			this.threadLocal.get().getSecond()
			.get()
			.uri("/authorization/generateDeviceID")
			.exchange()
			.expectBody(String.class)
			.consumeWith((device)->{
				this.threadLocal.get().setThird(device.getResponseBody());

			});
			deviceHeader.put(this.securityProperties.getTokenDeviceIdHeaderName(), this.threadLocal.get().getThird());
		}
		
		/**Metod return all UserAuthorizationHeader, including deviceIDHeader */
		private void getUserTokenHeader(Map<String,String>deviceHeader){
			UserAuthorizationDTO user=new UserAuthorizationDTO();
			//create fake profile and fill it with data
					UserComunicationDTO userProfile=new UserComunicationDTO();
					userProfile.setEmail(String.format("antonin.bicak%d@gmail.com", this.threadLocal.get().getFirst()));
					user.setProfile(userProfile);
					UserAuthPasswordDTOInput pas=new UserAuthPasswordDTOInput();
					pas.setPassword("Daret"+this.threadLocal.get().getFirst());
					user.setPassword(pas);
			//make registration 
		this.threadLocal.get().getSecond()
					.post()
					.uri("/authorization/register")
					.bodyValue(user)
					.headers(httpHeaders -> deviceHeader.forEach(httpHeaders::add))
					.exchange()

					.expectStatus().isOk()
					.expectBody(TokenDTO.class)
		.consumeWith((token)->{
			this.threadLocal.get().setThird(token.getResponseBody().getToken());
		});

		deviceHeader.put(this.securityProperties.getTokenAuthorizationUserHederName(), this.threadLocal.get().getThird());
		}
		private void getFullAuthorizatedUserHeader(Map<String,String> UserAuthorizatedHeader){
			UserProfileDTO prof=new UserProfileDTO();
			prof.setLastName("Bicak");
			prof.setSerName("Antonin");
			prof.setNickName("majner8");
		
			this.threadLocal.get().getSecond()
					.post()
					.uri("/authorization/finishRegistration")
					.bodyValue(prof)
					.headers(httpHeaders -> UserAuthorizatedHeader.forEach(httpHeaders::add))
					.exchange()
					.expectStatus().isOk()
					.expectBody(TokenDTO.class)
					.consumeWith((token)->{
					this.threadLocal.get().setThird(token.getResponseBody().getToken());
		});
		
			UserAuthorizatedHeader.remove(this.securityProperties.getTokenAuthorizationUserHederName());
			UserAuthorizatedHeader.put(this.securityProperties.getTokenAuthorizationUserHederName(), this.threadLocal.get().getThird());
		
		}


}
