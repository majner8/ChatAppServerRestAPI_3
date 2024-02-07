package ChatAPP_RabitMQ.Consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Data.Util.AbstractMultiInstanceBeanValidator;

public interface RabbitMQConsumerManager {

	public void startConsume(String userdeviceID,StompHeaderAccessor headerAccessor);
	public void stopConsume(String userdeviceID,StompHeaderAccessor headerAccessor);


	@Component
	@Primary
	public static class RabbitMQConsumerManagerInterfaceService  extends AbstractMultiInstanceBeanValidator implements RabbitMQConsumerManager{

		private List<RabbitMQConsumerManager> list;
		@Autowired
		public RabbitMQConsumerManagerInterfaceService(List<RabbitMQConsumerManager> list) {
			super(list,RabbitMQConsumerManagerInterfaceService.class); 
			this.list =list;
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
