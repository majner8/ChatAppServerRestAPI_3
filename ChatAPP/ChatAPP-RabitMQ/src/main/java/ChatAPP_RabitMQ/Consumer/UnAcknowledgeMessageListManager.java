package ChatAPP_RabitMQ.Consumer;

import com.rabbitmq.client.Channel;

public interface UnAcknowledgeMessageListManager {
	
	public void addMessageToList(String queueNameId,String messageID,Channel channel,long deliveryTag,boolean haveToBeMessageRequired);
}
