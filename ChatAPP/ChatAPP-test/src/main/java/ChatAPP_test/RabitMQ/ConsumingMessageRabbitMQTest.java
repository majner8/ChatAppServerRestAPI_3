package ChatAPP_test.RabitMQ;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManager.RabitMQQueue;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManagerInterface;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession.RabitMQThreadLocalSessionValue;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes=Main.Main.class)
public class ConsumingMessageRabbitMQTest {

	@Autowired
	private RabbitMQQueueManagerInterface QueueManag;

	@SpyBean
	private WebSocketThreadLocalSessionValue ses;
	@SpyBean
	@Qualifier("customChannelAwareMessageListener")
	private ChannelAwareMessageListener listener;

	@Autowired
	private RabitMQMessageProducerInterface push;

	@MockBean
	private RabitMQThreadLocalSessionValue rabitMQPropertiesThreadLocal;
    private final ObjectMapper objectMapper = new ObjectMapper();

	private static final long userID=5;
	private static final String connectionID="AAA";



	 @BeforeEach
	   public void setUp() throws Exception {
	        MockitoAnnotations.openMocks(this);
	        this.initMockBean();
	        this.initSpyBean();
	    }

	 private void initMockBean() {
		 Mockito.when(this.rabitMQPropertiesThreadLocal.getRabitMQPriority()).thenReturn(5);
		 Mockito.when(this.rabitMQPropertiesThreadLocal.isHaveToBeMessageReDeliver()).thenReturn(true);
		 Mockito.when(this.rabitMQPropertiesThreadLocal.getWebSocketEndPointPath()).thenReturn("/aahoooj");
	 }

	 private void initSpyBean() throws Exception {
		    Mockito.doReturn(ConsumingMessageRabbitMQTest.connectionID).when(ses).getConnectionID();
	        Mockito.doReturn(ConsumingMessageRabbitMQTest.userID).when(ses).getSessionOwnerUserID();
	 }

	 @Test
	 @Order(1)
	 public void createQueue() {
		 RabitMQQueue queue=this.QueueManag.getDeviceQueueName();
		 assertEquals(queue.isWasQueueCreated(),true);
	}
	 private static String messageID="Ahojjas";
	@Test
	@Order(2)
	public void pushMessageToRabbitMQ() {
		this.push.PushMessageToRabitMQ(new DTOJustForTest(ConsumingMessageRabbitMQTest.messageID), ConsumingMessageRabbitMQTest.userID);

		}
	@Test
	@Order(3)
	public void ConsumeMessageFromRabbitMQTest() throws Exception  {
        final CountDownLatch latch = new CountDownLatch(1);

		Mockito.doAnswer((invocation)->{
			try {
        	Message mes=(Message)invocation.getArgument(0);
 //       	ConsumingMessageRabbitMQTest.dto=this.objectMapper.readValue(mes.getBody(), DTOJustForTest.class);
			}
			finally {
				latch.countDown();
			}
        	return null;
        }).when(this.listener).onMessage(any(org.springframework.amqp.core.Message.class),any(com.rabbitmq.client.Channel.class));

		//Start Consume
//		SimpleMessageListenerContainer container =
//		this.ListenerContainerManager.createSimpleMessageListenerContainer(this.ses.getConnectionID());
//	container.start();
        boolean completed = latch.await(5, TimeUnit.SECONDS);

        if(!completed) {
    		assertTrue(false);
        }
        else {
    		//Assertions.assertEquals(ConsumingMessageRabbitMQTest.messageID,dto.getMessageID());
        }
	}


	private static class DTOJustForTest /*implements DTO*/{
		private  String MessageID;
		public DTOJustForTest(String messageID){
			this.MessageID=messageID;
		}
		public DTOJustForTest() {

		}

		public void setMessageID(String messageID) {
			MessageID = messageID;
		}
		}
}
