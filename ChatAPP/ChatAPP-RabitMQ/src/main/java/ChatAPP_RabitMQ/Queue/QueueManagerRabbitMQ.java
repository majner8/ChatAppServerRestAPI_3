package ChatAPP_RabitMQ.Queue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.AOP.BeanInitAnnotation.First;
import chatAPP_CommontPart.ApplicationListener.WebSocketSessionListener;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

/**Class manage declare and bind Queue for connected userDevice */
@First
@Component
public class QueueManagerRabbitMQ implements WebSocketSessionListener {
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue 
	webSocketSession;
	@Autowired
	private TopicExchange topicExchange; //have to be created bean
	@Autowired
	private AmqpAdmin amqpAdmin;
	@Override
	public void UserConnect() {
		if(Log4j2.log.isDebugEnabled()) {
			Log4j2.log.debug(Log4j2.MarkerLog.RabitMQ.getMarker(),String.format("Chech/declare userDevice Queue. Queue name: %s ",
					this.webSocketSession.getConnectionID()));
		}
		
		String queueName=this.webSocketSession.getConnectionID();
		if(this.amqpAdmin.getQueueInfo(queueName)!=null) {
			// queue already exist
			return;
		}
		Queue que=new Queue(queueName,true,false,false);
		this.amqpAdmin.declareQueue(que);
	    Binding binding = BindingBuilder.bind(que).to(this.topicExchange).with(String.valueOf(this.webSocketSession.getSessionOwnerUserID()));
	    this.amqpAdmin.declareBinding(binding);
		return;
	}

	@Override
	public void UserDisconnect() {
		// TODO Auto-generated method stub
		
	}

}
