package ChatAPP_RabitMQ.Consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

public interface RabbitMQConsumerManager {

	public void startConsume(String userdeviceID,StompHeaderAccessor headerAccessor);
	public void stopConsume(String userdeviceID,StompHeaderAccessor headerAccessor);


	@Component
	@Primary
	public static class RabbitMQConsumerManagerInterfaceService implements RabbitMQConsumerManager{

		private List<RabbitMQConsumerManager> list;
		@Autowired
		public RabbitMQConsumerManagerInterfaceService(List<RabbitMQConsumerManager> list) {
			  this.list = list.stream()
                      .filter(manager -> !(manager instanceof RabbitMQConsumerManagerInterfaceService))
                      .collect(Collectors.toList());
		}
		@Override
		public void startConsume(String userdeviceID,StompHeaderAccessor headerAccessor) {
			this.list.forEach((X)->{
				X.startConsume(userdeviceID,headerAccessor);
			});
		}

		@Override
		public void stopConsume(String userdeviceID,StompHeaderAccessor headerAccessor) {
			this.list.forEach((X)->{
				X.stopConsume(userdeviceID, headerAccessor);
			});			
		}
		
	}
}
