package ChatAPP.Test.Authorization;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("chatAPP-Test")
public class AuthorizationTest {

	@Autowired
	private CreateAuthorizationAcessTest access;
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void makeRegistration() {
		this.access.getAuthorizationHeaders(webTestClient);
		this.access.getAuthorizationHeaders(webTestClient);
		this.access.getAuthorizationHeaders(webTestClient);	
		this.access.getAuthorizationHeaders(webTestClient);
	}
}
