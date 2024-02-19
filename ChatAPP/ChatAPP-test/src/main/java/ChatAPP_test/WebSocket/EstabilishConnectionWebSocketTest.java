package ChatAPP_test.WebSocket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import chatAPP_CommontPart.Log4j2.Log4j2;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EstabilishConnectionWebSocketTest {

    @LocalServerPort
    private int port;
    private WebSocketStompClient stompClient;
    @Value("${websocket.stoamp.endpoint}")
   	private String webSocketStoamppreflix;
    @BeforeEach
    public void setup() {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
    }
    
    
    @Test
    public void MakeConnection() throws InterruptedException  {
    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
      
         };         
         WebSocketHttpHeaders hed;
         
         String handshakePath="ws://localhost:"+this.port+"//"+webSocketStoamppreflix;
         Log4j2.log.info(Log4j2.MarkerLog.Test.getMarker(),"EstabilishConnectionTest, handshakePAth: "+handshakePath);
        ListenableFuture< StompSession> ses=
        		 this.stompClient.connect(handshakePath, null, sessionHandler,new Object [0]);
        	try {
				ses.get(10, TimeUnit.SECONDS);
			} 
        	
        	catch (ExecutionException e) {
        	
        		if(e.getCause()!=null) {
        			//it end by exception, 401 response-does not contain device and User jwtToken
        				assertTrue(e.getCause()instanceof javax.websocket.DeploymentException);
        				
        		}
        		else {
        			fail(e);
        		}
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
        		fail("Cannot connect with server, in set time");
			}	 
    	 
    }
}
