package ChatAPP_test.WebSocket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ChatAPP_test.Authorization.jwtTokenTestAuthorizationToken;
import chatAPP_CommontPart.Log4j2.Log4j2;
@Component
@Profile("test")
public class MakeConnectionTOWebSocketTest {

	@Autowired
	private jwtTokenTestAuthorizationToken autToken;
	
	private static String handShakePath;
	
    private WebSocketStompClient stompClient;
    @Value("${websocket.stoamp.endpoint}")
   	private String webSocketStoamppreflix;
    
    @BeforeEach
    public void init() {
    	// Create a list of transports, including SockJS fallback options
    	List<Transport> transports = new ArrayList<>();
    	transports.add(new WebSocketTransport(new StandardWebSocketClient()));

    	// Initialize the SockJsClient with the transports
    	SockJsClient sockJsClient = new SockJsClient(transports);

    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.registerModule(new JavaTimeModule()); // Register module for Java 8 Date/Time support    
    	objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    	MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    	messageConverter.setObjectMapper(objectMapper);
        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(messageConverter);
        this.stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        this.handShakePath="ws://localhost:"+"%d"+"//"+webSocketStoamppreflix;

    }

   
	  public StompSession makeConnectionToServer(int serverPort) throws InterruptedException {
	    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
	    	   
	         };         
	         WebSocketHttpHeaders authorizationHeader=new WebSocketHttpHeaders() ;
	         this.autToken.getAuthorizationHeaders().forEach(
	        		 (K,V)->{
	        			 Log4j2.log.debug(Log4j2.MarkerLog.Test.getMarker(),"Authorization HeaderName: "+K);
	        			 Log4j2.log.debug(Log4j2.MarkerLog.Test.getMarker(),"Authorization Value: "+V);
	        			 authorizationHeader.add(K, V);
	        		 }
	        		 );
	         assertTrue(!authorizationHeader.isEmpty());
	         ListenableFuture< StompSession> ses=
	        		 this.stompClient.connect(String.format(handShakePath,serverPort), authorizationHeader, sessionHandler,new Object [0]);
	        	try {
	        		StompSession x=ses.get(10, TimeUnit.SECONDS);
					assertTrue(true);
					return x;
				} 
	        	
	        	catch (ExecutionException e) {
	        		fail(e);
	        		
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
	        		fail("Cannot connect with server, in set time");
				}	 
	        	return null;
	    }


}
