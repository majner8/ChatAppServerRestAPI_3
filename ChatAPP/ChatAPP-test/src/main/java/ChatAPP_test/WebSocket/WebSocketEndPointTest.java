package ChatAPP_test.WebSocket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import ChatAPP_WebSocket.WebSocketEndPointPath;
import ChatAPP_WebSocket.Service.Chat.ProcessChatMessageService;
import ChatAPP_test.Authorization.jwtTokenTestAuthorizationToken;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_DTO.Message.MessageDTO;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class WebSocketEndPointTest {

    @LocalServerPort
    private int port;
    private WebSocketStompClient stompClient;
    @Value("${websocket.stoamp.endpoint}")
   	private String webSocketStoamppreflix;
    @Autowired
    private jwtTokenTestAuthorizationToken autToken;
    @SpyBean
    private ProcessChatMessageService process;
    private static MessageDTO fakeMessage;
	    @BeforeEach
    public void setup() {

			this.fakeMessage=new MessageDTO();
			this.fakeMessage.setChatID("");
			this.fakeMessage.setMessage("");
			this.fakeMessage.setMessageID("");
			this.fakeMessage.setOrder(0);
			this.fakeMessage.setReceivedTime(LocalDateTime.now());
			this.fakeMessage.setReferencMessageID("");
			this.fakeMessage.setSenderID(0);
			this.fakeMessage.setVersion(0);
			this.fakeMessage.setWasMessageRemoved(true);

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
        this.initMock();
    }
    
    private void initMock() {
    	Mockito.doAnswer((x)->{
    		for(int i=0;i<10;i++) {
    			Log4j2.log.info("");
    		}
			Log4j2.log.info("Retrieve Message, from SendMessage");

    		return null;
    	}).when(this.process).SendMessage(Mockito.any(), Mockito.any());;
    }
    private static String handshakePath;
   
    @Order(1)
    @Test
    public void MakeConnectionWithoutAuthrozation() throws InterruptedException  {
    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
    		 @Override
    		 public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    			 assertTrue((exception instanceof  javax.websocket.DeploymentException));
    		 }
         }
    	 ;         
      //   WebSocketHttpHeaders authorizationHeader=new WebSocketHttpHeaders() ;
         
      //   this.autToken.getAuthorizationHeaders().forEach(authorizationHeader::add);
       
         ListenableFuture< StompSession> ses=
        		 this.stompClient.connect(handshakePath, null, sessionHandler,new Object [0]);
        	try {
				ses.get(10, TimeUnit.SECONDS);
			} 
        	
        	catch (ExecutionException e) {
  
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
        		fail("Cannot connect with server, in set time");
			}	 
    	 
    }
    
    @Test
    @Order(3)
    public void MakeConnectionWithAuthorization() throws InterruptedException  {
    	this.makeConnectionToServer();
    	 
    }

    @Test
    @Order(2)
    public void TryToSendMessage() throws InterruptedException {
    	StompSession ses=this.makeConnectionToServer();
    	ses.send(WebSocketEndPointPath.Chat_SendMessagePath, this.fakeMessage);
    }
    public StompSession makeConnectionToServer() throws InterruptedException {
    	 StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
    	      
         };         
         WebSocketHttpHeaders authorizationHeader=new WebSocketHttpHeaders() ;
         
         this.autToken.getAuthorizationHeaders().forEach(authorizationHeader::add);
         ListenableFuture< StompSession> ses=
        		 this.stompClient.connect(handshakePath, authorizationHeader, sessionHandler,new Object [0]);
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
