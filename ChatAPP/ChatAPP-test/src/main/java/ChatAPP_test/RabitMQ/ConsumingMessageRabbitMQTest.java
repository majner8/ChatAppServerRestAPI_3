package ChatAPP_test.RabitMQ;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import ChatAPP_RabitMQ.Queue.RabbitMQQueueManagerInterface;
import ChatAPP_RabitMQ.RabitMQProperties;
import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManager.RabitMQQueue;
import ChatAPP_WebSocket.WebSocketEndPointPath;
import chatAPP_CommontPart.AOP.RabitMQAnnotationAOP;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession.RabitMQThreadLocalSessionValue;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue;
import chatAPP_DTO.DTO;
import chatAPP_DTO.Message.MessageDTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes=Main.Main.class)
public class ConsumingMessageRabbitMQTest {

	@Autowired
	private RabbitMQQueueManagerInterface QueueManag;

	
	@SpyBean
	private WebSocketThreadLocalSessionValue ses;
	@Autowired
	private RabitMQMessageProducerInterface push;

	@MockBean
	private RabitMQThreadLocalSessionValue rabitMQPropertiesThreadLocal;
	
	private static final long userID=(long)5;
	private static final String connectionID="AAA";
	 @BeforeEach
	   public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        this.initMockBean();
	        this.initSpyBean();
	    }

	 private void initMockBean() {
		 Mockito.when(this.rabitMQPropertiesThreadLocal.getRabitMQPriority()).thenReturn(5);
		 Mockito.when(this.rabitMQPropertiesThreadLocal.isHaveToBeMessageReDeliver()).thenReturn(true);
		 Mockito.when(this.rabitMQPropertiesThreadLocal.getWebSocketEndPointPath()).thenReturn("/aahoooj");

	 }
	 private void initSpyBean() {
		    Mockito.doReturn(this.connectionID).when(ses).getConnectionID();
	        Mockito.doReturn(this.userID).when(ses).getSessionOwnerUserID();
	        
	 }
	 
	 @Test
	 @Order(1)
	 public void createQueue() {
		 RabitMQQueue queue=this.QueueManag.getDeviceQueueName();
		 assertEquals(queue.isWasQueueCreated(),true);
	}
	@Test
	@Order(2)
	public void pushMessageToRabbitMQ() {
		this.PushMessageToRabbitMQAnnotation();
	}
	private void PushMessageToRabbitMQAnnotation() {
		this.push.PushMessageToRabitMQ(()->{
			return "Ahojjas";
		}, userID);
	
	}


	 

	
}
