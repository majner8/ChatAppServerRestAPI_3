package ChatAPP.Test.WebSocket;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import ChatAPP.Test.Authorization.CreateAuthorizationAcessTest;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("chatAPP-Test")
public class HandShakeTest {

	@Autowired
	private CreateAndgetWebSocketSessionTest access;

	@LocalServerPort
	private int port;
	@Autowired
	private WebTestClient webTestClient;
	@Test
	public void makeConnection()  {
		this.access.makeConnectionToServer(port,webTestClient);
	};
	
}
