package ChatAPP_test.FullTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import ChatAPP_RabitMQ.Consumer.RabbitMQConsumerManager;
import ChatAPP_test.Authorization.jwtTokenTestAuthorizationToken;
import ChatAPP_test.WebSocket.MakeConnectionTOWebSocketTest;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_DTO.Chat.CreateChatDTO;

@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class CreateChatWebSocketTest {

	@MockBean
	private RabbitMQConsumerManager rabitMQConsumer;
	
	@Autowired
	private MakeConnectionTOWebSocketTest ws;
	@Autowired
	private WebTestClient webTestClient;
	@LocalServerPort
	private int port;
	@Autowired
	private jwtTokenTestAuthorizationToken autToken;
	@Autowired
    private ApplicationContext context;
	private CreateChatDTO dto;
	private String handShakePath;

	@Value("${websocket.stoamp.endpoint}")
   	private String webSocketStoamppreflix;
    
	@BeforeEach
	public void init() {
	    this.handShakePath="ws://localhost:"+this.port+"//"+webSocketStoamppreflix;

		this.dto=new CreateChatDTO()
				.setCreatedBy(1)
				.setOtherUser(new long[] {0});
		
	    assertTrue(3308 == this.port, "The server is running on port " + this.port + " instead of 3308");
	    this.initMock();
	}
	private void initMock() {
		Mockito.doNothing()
		.when(this.rabitMQConsumer).startConsume(Mockito.anyString(),Mockito.any());
	}
	@Test
	@Order(1)
	public void TryCreateChat() throws InterruptedException {
		//just call it, it manage register new User, ID will be 0, if database is empty
		this.autToken.getAuthorizationHeaders(this.webTestClient);
		
		StompSession ses=this.ws.makeConnectionToServer(this.port,this.webTestClient,this.handShakePath);
		ses.send("/app"+WebSocketEndPointPath.createChatEndPoint, this.dto);
		assertTrue(true);
	}
	//@MessageMapping(WebSocketEndPointPath.createChatEndPoint)
	
}

		