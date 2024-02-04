package ChatAPP_test.RabitMQ;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import ChatAPP_RabitMQ.Queue.RabbitMQQueueManager.RabitMQQueue;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManagerInterface;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue;


@SpringBootTest(classes=Main.Main.class)
public class DynamicQueueManagementTest {
	
	@Autowired
	private RabbitMQQueueManagerInterface QueueManag;

	
	@SpyBean
	private WebSocketThreadLocalSessionValue ses;
	
	 @BeforeEach
	   public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        Mockito.doReturn("AAA").when(ses).getConnectionID();
	        Mockito.doReturn((long)5).when(ses).getSessionOwnerUserID();


	    }

	 @Test
	 public void createQueue() {
		 RabitMQQueue queue=this.QueueManag.getDeviceQueueName();
		 assertEquals(queue.isWasQueueCreated(),true);
	}
	 @Test
	 public void tryCreateQueue() {
		 RabitMQQueue queue=this.QueueManag.getDeviceQueueName();
		 assertEquals(queue.isWasQueueCreated(),false);
	}
}
