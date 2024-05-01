package ChatAPP.Test.WebSocket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ChatAPP.Test.TestProperties;
import ChatAPP.Test.Authorization.CreateAuthorizationAcessTest;

@Component
@Profile("chatAPP-Test")
public class CreateAndgetWebSocketSessionTest {

	@Autowired
	private CreateAuthorizationAcessTest authorization;
	@Autowired
	private TestProperties properties;
    private WebSocketStompClient stompClient;

    public  CreateAndgetWebSocketSessionTest() {
    	this.init();
    }
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
    }


	  public StompSession makeConnectionToServer(int serverPort,WebTestClient web) {
	    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {

	         };

	         WebSocketHttpHeaders authorizationHeader=new WebSocketHttpHeaders() ;
	         this.authorization.getAuthorizationHeaders(web)
	         
	         .forEach(
	        		 (K,V)->{
	        			 authorizationHeader.add(K, V);
	        		 }
	        		 );
	         assertTrue(!authorizationHeader.isEmpty());
	         ListenableFuture< StompSession> ses=
	        		 this.stompClient.connect(this.properties.getWebSocketEndPointPath(serverPort), authorizationHeader, sessionHandler,new Object [0]);
	        	try {
	        		StompSession x;
					try {
						x = ses.get(10, TimeUnit.SECONDS);
						assertTrue(true);

						return x;

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block

						fail(e);
						}
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
