package ChatAPP_RabitMQ.Consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

public interface RabbitMQConsumerManager {

	public void startConsume(String userdeviceID);
	public void stopConsume(String userdeviceID,boolean DoesDeviceDisconect);


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
		public void startConsume(String userdeviceID) {
			this.list.forEach((X)->{
				X.startConsume(userdeviceID);
			});
		}

		@Override
		public void stopConsume(String userdeviceID, boolean DoesDeviceDisconect) {
			this.list.forEach((X)->{
				X.stopConsume(userdeviceID, DoesDeviceDisconect);
			});			
		}
		
	}
}
