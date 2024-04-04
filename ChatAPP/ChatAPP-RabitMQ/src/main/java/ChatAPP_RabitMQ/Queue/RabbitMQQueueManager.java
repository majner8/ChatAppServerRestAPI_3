package ChatAPP_RabitMQ.Queue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

@Component
public class RabbitMQQueueManager implements RabbitMQQueueManagerInterface {

	@Autowired
	private AmqpAdmin amqpAdmin;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue simpMessage;
	@Autowired
	private TopicExchange topicExchange; //have to be created bean
	@Override
	public RabitMQQueue getDeviceQueueName() {
		String queueName=this.simpMessage.getConnectionID();
		if(this.amqpAdmin.getQueueInfo(queueName)!=null) {
			return new RabitMQQueue(queueName,false);
		}
		Queue q=this.createuserDeviceQueue(queueName);
		this.BindQueue(this.simpMessage.getSessionOwnerUserID(),q);
		return new RabitMQQueue(queueName,true);
	}

	private void BindQueue(long userID,Queue userDdeviceQueue) {
		  // Declare and bind the queue
	    Binding binding = BindingBuilder.bind(userDdeviceQueue).to(this.topicExchange).with(String.valueOf(userID));
	    this.amqpAdmin.declareBinding(binding);
	}
	private Queue createuserDeviceQueue(String queueName) {
		Queue que=new Queue(queueName,true,false,false);
		this.amqpAdmin.declareQueue(que);
		return que;
	}

	/**class, contain rabitMQ Queue name, and boolean value, if Queue was created, in this request */
	public final static class RabitMQQueue {
		private final String queueName;
		private final boolean wasQueueCreated;

		public RabitMQQueue(String queueName, boolean wasQueueCreated) {
			this.queueName = queueName;
			this.wasQueueCreated = wasQueueCreated;
		}
		public String getQueueName() {
			return queueName;
		}
		public boolean isWasQueueCreated() {
			return wasQueueCreated;
		}

	}
}
