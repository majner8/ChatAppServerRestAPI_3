package ChatAPP_test.WebSocket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import ChatAPP_test.Authorization.jwtTokenTestAuthorizationToken;
import chatAPP_CommontPart.Log4j2.Log4j2;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class EstabilishConnectionWebSocketTest {

    @LocalServerPort
    private int port;
    private WebSocketStompClient stompClient;
    @Value("${websocket.stoamp.endpoint}")
   	private String webSocketStoamppreflix;
    @Autowired
    private jwtTokenTestAuthorizationToken autToken;
    @BeforeEach
    public void setup() {
    	// Create a list of transports, including SockJS fallback options
    	List<Transport> transports = new ArrayList<>();
    	transports.add(new WebSocketTransport(new StandardWebSocketClient()));

    	// Initialize the SockJsClient with the transports
    	SockJsClient sockJsClient = new SockJsClient(transports);

        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        this.handshakePath="ws://localhost:"+this.port+"//"+webSocketStoamppreflix;
        Log4j2.log.info(Log4j2.MarkerLog.Test.getMarker(),"EstabilishConnectionTest, handshakePAth: "+handshakePath);
    }
    private static String handshakePath;
   
    @Order(1)
    @Test
    public void MakeConnectionWithoutAuthrozation() throws InterruptedException  {
    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
      
         };         
      //   WebSocketHttpHeaders authorizationHeader=new WebSocketHttpHeaders() ;
         
      //   this.autToken.getAuthorizationHeaders().forEach(authorizationHeader::add);
       
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
    
    @Test
    @Order(2)
    public void MakeConnectionWithAuthorization() throws InterruptedException  {
    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
      
         };         
         WebSocketHttpHeaders authorizationHeader=new WebSocketHttpHeaders() ;
         
         this.autToken.getAuthorizationHeaders().forEach(authorizationHeader::add);
         ListenableFuture< StompSession> ses=
        		 this.stompClient.connect(handshakePath, authorizationHeader, sessionHandler,new Object [0]);
        	try {
				ses.get(10, TimeUnit.SECONDS);
				assertTrue(true);
			} 
        	
        	catch (ExecutionException e) {
        		fail(e);
        		
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
        		fail("Cannot connect with server, in set time");
			}	 
    	 
    }

}
