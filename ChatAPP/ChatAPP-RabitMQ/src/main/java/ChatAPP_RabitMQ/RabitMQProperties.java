package ChatAPP_RabitMQ;

import org.springframework.stereotype.Component;

public interface RabitMQProperties {

	public long getUnacknowledgedMessageTimeout();
	
	public String getMessagePropertiesWebSocketEndPointName();
	
	public String getHaveToBeMessageRequiredHeaderName();
	
	public String getContainerHeaderName();
	
	@Component
	public static class RabitMQPropertiesClass implements RabitMQProperties{

		@Override
		public long getUnacknowledgedMessageTimeout() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getMessagePropertiesWebSocketEndPointName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHaveToBeMessageRequiredHeaderName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContainerHeaderName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
