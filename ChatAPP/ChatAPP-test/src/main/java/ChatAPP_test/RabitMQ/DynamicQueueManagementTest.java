package ChatAPP_test.RabitMQ;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.annotation.Order;

import ChatAPP_RabitMQ.Queue.RabbitMQQueueManager.RabitMQQueue;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManagerInterface;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes=Main.Main.class)
public class DynamicQueueManagementTest {

	@Autowired
	private RabbitMQQueueManagerInterface QueueManag;

	@Autowired
	private RabbitAdmin amqpAdmin;
	@SpyBean
	private WebSocketThreadLocalSessionValue ses;

	 @BeforeEach
	   public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        Mockito.doReturn("AAA").when(ses).getConnectionID();
	        Mockito.doReturn((long)5).when(ses).getSessionOwnerUserID();


	    }
	 @Order(1)
	 @Test
	 public void createQueue() {
		 assertEquals(this.CreateQueue().isWasQueueCreated(),true);
	}
	 @Order(2)
	 @Test
	 public void tryCreateQueue() {
		 assertEquals(this.CreateQueue().isWasQueueCreated(),false);
	}
	 @Order(3)
	 @Test
	 public void removeQueueAndTryItAgain() {
		 this.amqpAdmin.deleteQueue(this.ses.getConnectionID());
		 assertEquals(this.CreateQueue().isWasQueueCreated(),true);
		 assertEquals(this.CreateQueue().isWasQueueCreated(),false);
		 this.amqpAdmin.deleteQueue(this.ses.getConnectionID());

	 }

	 private RabitMQQueue CreateQueue() {
		 return this.QueueManag.getDeviceQueueName();
	 }
}
