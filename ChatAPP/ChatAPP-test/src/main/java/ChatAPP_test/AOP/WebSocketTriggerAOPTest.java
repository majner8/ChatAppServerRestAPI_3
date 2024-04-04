package ChatAPP_test.AOP;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_Security.RequestPermision.MessageRequestPermision;
import ChatAPP_WebSocket_EndPoint.EndPoint.Chat.ChatAction.WebSocketChatComunicationEndPoint;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession.RabitMQThreadLocalSessionValue;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_DTO.Message.MessageDTO;
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
	private WebSocketChatComunicationEndPoint processChatMessage;
	@Autowired
	private RabitMQThreadLocalSessionValue rabbitMQAOP;

	@SpyBean RabitMQThreadLocalSession rabbitSession;
	private static MessageDTO fakeMessage;
	@BeforeEach
	public void setUp() {
		WebSocketTriggerAOPTest.fakeMessage=new MessageDTO();
		WebSocketTriggerAOPTest.fakeMessage.setChatID("");
		WebSocketTriggerAOPTest.fakeMessage.setMessage("");
		WebSocketTriggerAOPTest.fakeMessage.setMessageID("");
		WebSocketTriggerAOPTest.fakeMessage.setOrder(0);
		WebSocketTriggerAOPTest.fakeMessage.setReceivedTime(LocalDateTime.now());
		WebSocketTriggerAOPTest.fakeMessage.setReferencMessageID("");
		WebSocketTriggerAOPTest.fakeMessage.setSenderID(0);
		//WebSocketTriggerAOPTest.fakeMessage.setVersion(0);
		WebSocketTriggerAOPTest.fakeMessage.setWasMessageRemoved(true);
		AtomicInteger count = new AtomicInteger();
		count.set(0);
		Mockito.doAnswer((I)->{
			if(count.get()>=1) {
				return I.callRealMethod();
			}
			count.incrementAndGet();
			return null;}).when(this.rabbitSession).clear();
		Mockito.doNothing().when(this.rabbitSession).clear();
		Mockito.doNothing().when(this.SecurityVerification).verifyUserAccestPermisionToChat(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
		//Mockito.doReturn(this.fakeMessage).when(this.messageRepo).convertDTOToEntity(Mockito.any());
		Mockito.doNothing().when(this.rabitMQPush).PushMessageToRabitMQ(ArgumentMatchers.any(), ArgumentMatchers.anySet());
		Mockito.doReturn(new HashSet<Long>()).when(this.chatManagement).getUserIDofMembers(ArgumentMatchers.anyString(), ArgumentMatchers.anyBoolean());

	}
	@Test
	public void testRabbitMQAOP() {
		this.processChatMessage.SendMessage(fakeMessage, null);

		assertTrue(MessageDTO.class==this.rabbitMQAOP.getDTOClass());
		assertTrue(this.rabbitMQAOP.getWebSocketEndPointPath().equals(WebSocketEndPointPath.Chat_SendMessagePath));
		assertTrue(this.rabbitMQAOP.isHaveToBeMessageReDeliver());
		this.rabbitSession.clear();
	}
}
