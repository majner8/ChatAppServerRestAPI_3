package ChatAPP_RabitMQ.Producer;

import java.util.List;
import java.util.Set;

import chatAPP_DTO.DTO;


/**Interface set couple of metod, which manage pushing message to rabitMQ.
 * Before use this interface, appropriate rabitMQ setting such as priory e.t.c on ThreadLocalSimMessageHeader have to be set);
	 */
public interface RabitMQMessageProducerInterface {


	public void PushMessageToRabitMQ(DTO message,long UserRecipientId);
	public default void PushMessageToRabitMQ(DTO message,Set<Long> UserRecipientIds) {
		UserRecipientIds.forEach((x)->{
			this.PushMessageToRabitMQ(message, x);
		});
	}
	
	public void PushMessageToRabitMQ(DTO message, String queueName);
	public default void PushMessageToRabitMQ(List<DTO> messages,String queueName) {
		synchronized(messages) {
			messages.forEach((X)->{
				this.PushMessageToRabitMQ(X, queueName);
			});
		}
	}
	
	public void pushMessageToRabitMQ(String chatID,DTO... message);
	}
