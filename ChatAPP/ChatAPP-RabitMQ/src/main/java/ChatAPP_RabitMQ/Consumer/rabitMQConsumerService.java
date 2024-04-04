package ChatAPP_RabitMQ.Consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import ChatAPP_RabitMQ.RabitMQProperties;
import ChatAPP_RabitMQ.Listener.RabbitMQMessageRelayInterface;

@Component
@Qualifier("customChannelAwareMessageListener")
public class rabitMQConsumerService implements ChannelAwareMessageListener{
	@Autowired
	private UnAcknowledgeMessageListManager messageManager;
	@Autowired
	private RabitMQProperties RabitMQProperties;
	@Autowired
	private RabbitMQMessageRelayInterface relay;
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {

		MessageProperties properties=message.getMessageProperties();
		String messageID=properties.getMessageId();
		String recipientID=properties.getConsumerQueue();
		boolean haveToBeMessageRequired=properties.getHeader(this.RabitMQProperties.getHaveToBeMessageRequiredHeaderName());
		long deliveryTag=properties.getDeliveryTag();
		String webSocketEndPoint=properties.getHeader(this.RabitMQProperties.getMessagePropertiesWebSocketEndPointHeaderName());
//		Name is same as userdeviceID, userID+deviceID
		this.messageManager.addMessageToList(recipientID,messageID, channel, deliveryTag, haveToBeMessageRequired);

		//message can be convert as String, because to rabitMQ is push in string JSON format.
		String convertMessage=new String(message.getBody());
		this.relay.SendConsumedMessage(webSocketEndPoint, messageID, convertMessage, recipientID);
	}



}
