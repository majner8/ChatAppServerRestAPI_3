package ChatAPP_test.RabitMQ;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ChatAPP_RabitMQ.Queue.RabbitMQQueueManagerInterface;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue;


@SpringBootTest(classes=Main.Main.class)
public class DynamicQueueManagementTest {
	
	@Autowired
	private RabbitMQQueueManagerInterface QueueManag;

	
	@MockBean
	private WebSocketThreadLocalSessionValue ses;
	
	 @BeforeEach
	   public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        Mockito.when(WebSocketThreadLocalSessionValue).thenReturn("Mocked Response");
	    }
	public void createQueue() {
		
	}
}
