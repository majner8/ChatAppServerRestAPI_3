package ChatAPP_test.WebSocket;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_Security.RequestPermision.MessageRequestPermision;
import ChatAPP_WebSocket.WebSocketEndPointPath;
import ChatAPP_WebSocket.Service.Chat.ProcessChatMessageService;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession.RabitMQThreadLocalSessionValue;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_database.Chat.Messages.MessageEntity;
import chatAPP_database.Chat.Messages.MessageRepositoryEntity;
@SpringBootTest(classes=Main.Main.class)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")

public class WebSocketTriggerAOPTest {

	@SpyBean
	private MessageRepositoryEntity messageRepo;
	@SpyBean
	private MessageRequestPermision SecurityVerification;
	@SpyBean
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue sessionAttributeInterface;
	@SpyBean
	private ChatManagementInterface chatManagement;
	@SpyBean
	private RabitMQMessageProducerInterface rabitMQPush;

	
	@Autowired
	private ProcessChatMessageService processChatMessage;
	@Autowired
	private RabitMQThreadLocalSessionValue rabbitMQAOP;

	private static MessageDTO fakeMessage;
	@BeforeEach
	public void setUp() {
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
		
		Mockito.doNothing().when(this.SecurityVerification).verifyUserAccestPermisionToChat(Mockito.anyLong(), Mockito.anyString());;
		//Mockito.doReturn(this.fakeMessage).when(this.messageRepo).convertDTOToEntity(Mockito.any());
		Mockito.doNothing().when(this.rabitMQPush).PushMessageToRabitMQ(Mockito.any(), Mockito.anySet());
		Mockito.doReturn(new HashSet<Long>()).when(this.chatManagement).getUserIDofMembers(Mockito.anyString(), Mockito.anyBoolean());
		
	}
	@Test
	public void testRabbitMQAOP() {
		this.processChatMessage.SendMessage(null, fakeMessage);

		assertTrue(MessageDTO.class==this.rabbitMQAOP.getDTOClass());
		assertTrue(this.rabbitMQAOP.getWebSocketEndPointPath().equals(WebSocketEndPointPath.Chat_SendMessagePath));
		assertTrue(this.rabbitMQAOP.isHaveToBeMessageReDeliver());
	}	
}
