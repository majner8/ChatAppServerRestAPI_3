package ChatAPP_test.Authorization;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import ChatAPP_Security.Authorization.DeviceID.DeviceIDGenerator;
import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_DTO.User.UserDTO.UserAuthPasswordDTO;
import chatAPP_DTO.User.UserDTO.UserAuthorizationDTO;
import chatAPP_DTO.User.UserDTO.UserComunicationDTO;
import chatAPP_DTO.User.UserDTO.UserProfileDTO;

@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class jwtTokenTest {

	@Autowired
    private WebTestClient webTestClient;
	 @Autowired
	private SecurityProperties securityProperties;
	@SpyBean
	private DeviceIDGenerator generator;
	@BeforeEach
	   public void setUp() {
//	        MockitoAnnotations.openMocks(this);
		this.initRegistrationUser();
		this.initMockBean();
	    }
	private void initMockBean() {
		 // Using an Answer to delegate to the real method after the first call
	    AtomicInteger count = new AtomicInteger();
	    Mockito.when(this.generator.generateDeviceID()).thenAnswer(invocation -> {

            if (count.getAndIncrement() == 0) {
	            return "03df76fc-a253-4003-a505-56a8c8e57436"; // First call returns this
	        } else {
	            return invocation.callRealMethod(); // Subsequent calls invoke the real method
	        }
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
	private UserAuthorizationDTO user;
	
	private static String deviceIDToken="523SA";

	@Test()
	@Order(1)
	public void TestDeviceIDGenerator() {

		//have to set static generate String on UUID
		 ResponseSpec deviceIDToken=this.webTestClient
					.get()
					.uri("/authorization/generateDeviceID")			
					.exchange()	;
					deviceIDToken.expectStatus().isOk()
					.expectBody(String.class)
					.consumeWith((device)->{
						this.deviceIDToken=device.getResponseBody();
					})
					;	
		
	}

	
	@Test
	@Order(5)
	public void getDeviceIDTokenTest() {
		ResponseSpec deviceIDToken=this.webTestClient
				.get()
				.uri("/authorization/generateDeviceID")			
				.exchange()	;
				deviceIDToken.expectStatus().isOk()
				.expectBody(String.class)
				.consumeWith((device)->{
					this.deviceIDToken=device.getResponseBody();
				})
				;	
	}

	@Test
	@Order(6)
	public void TestAuthorizatedPath() {
		ResponseSpec res=this.webTestClient
		.post()
		.uri("/authorization/finishRegistration")
		.exchange()	;
		res.expectStatus().isUnauthorized();
		
		ResponseSpec registration=this.webTestClient
				.post()
				.uri("/authorization/register")
				.bodyValue(user)
				.exchange();
		
		//because does not send jwtToken deviceID token
		registration.expectStatus().isUnauthorized();
		
	}
	
	@Test
	@Order(7)
	public void TryLogin() {
		ResponseSpec registration=this.webTestClient
				.post()
				.uri("/authorization/login")
				.bodyValue(user)
				.header(this.securityProperties.getTokenDeviceIdHeaderName(), this.deviceIDToken)
				.exchange();
		//because user is not registred
		registration.expectStatus().isUnauthorized();
	
	}
	@Test
	@Order(8)
	public void testRegistration() {
		
		 TokenDTO[] rawToken= new TokenDTO[1];
		 
			ResponseSpec registration=this.webTestClient
					.post()
					.uri("/authorization/register")
					.bodyValue(user)
					.header(this.securityProperties.getTokenDeviceIdHeaderName(), this.deviceIDToken)
					.exchange();
		registration.expectStatus().isOk()
		.expectBody(TokenDTO.class)
		.consumeWith((token)->{
			rawToken[0]=token.getResponseBody();
		});
	}
	
	@Test
	@Order(9)
	public void makeLogin() {
		ResponseSpec registration=this.webTestClient
				.post()
				.uri("/authorization/login")
				.bodyValue(user)
				.header(this.securityProperties.getTokenDeviceIdHeaderName(), this.deviceIDToken)
				.exchange();
		registration.expectStatus().isOk()
		.expectBody(TokenDTO.class)
		.consumeWith((token)->{
			this.authorizatedToken=token.getResponseBody().getToken();
		});
	}
	private static String authorizatedToken;
	@Test
	@Order(10)
	public void finishRegistration() {
		UserProfileDTO prof=new UserProfileDTO();
		prof.setLastName("Bicak");
		prof.setSerName("Antonin");
		prof.setNickName("majner8");
		
		ResponseSpec registration=this.webTestClient
				.post()
				.uri("/authorization/finishRegistration")
				.bodyValue(prof)
				.header(this.securityProperties.getTokenDeviceIdHeaderName(), this.deviceIDToken)
				.header(this.securityProperties.getTokenAuthorizationUserHederName(), this.authorizatedToken)
				.exchange();
	}
}
