package ChatAPP_test.Authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_DTO.User.UserDTO.UserAuthPasswordDTO;
import chatAPP_DTO.User.UserDTO.UserAuthorizationDTO;
import chatAPP_DTO.User.UserDTO.UserComunicationDTO;

@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class jwtTokenTest {

	@Autowired
    private WebTestClient webTestClient;

	@BeforeEach
	   public void setUp() {
//	        MockitoAnnotations.openMocks(this);

	    }
	
	@Test
	public void TestAuthorizatedPath() {
		ResponseSpec res=this.webTestClient
		.post()
		.uri("/authorization/finishRegistration")
		.exchange()	;
		res.expectStatus().isForbidden();
		//create fake profile and fill it with data
		UserAuthorizationDTO user=new UserAuthorizationDTO();
		UserComunicationDTO userProfile=new UserComunicationDTO();
		userProfile.setEmail("Antonin.bicak@gmail.com");
		userProfile.setPhone("5353");
		userProfile.setPhonePreflix("54");
		user.setProfile(userProfile);
		UserAuthPasswordDTO pas=new UserAuthPasswordDTO();
		pas.setPassword("dasdas");
		user.setPassword(pas);
		 TokenDTO[] rawToken= new TokenDTO[1];
		ResponseSpec registration=this.webTestClient
				.post()
				.uri("/authorization/register")
				.bodyValue(user)
				.exchange()	;
		
				res.expectStatus().isForbidden();
				registration.expectStatus().isOk()
				.expectBody(TokenDTO.class)
				.consumeWith((token)->{
					rawToken[0]=token.getResponseBody();
				});
	}
}
