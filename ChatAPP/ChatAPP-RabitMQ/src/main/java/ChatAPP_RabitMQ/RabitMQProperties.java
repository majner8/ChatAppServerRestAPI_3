package ChatAPP_RabitMQ;

import java.time.Duration;

public interface RabitMQProperties {

	public Duration getUnacknowledgedMessageTimeout();
	
	public String getMessagePropertiesWebSocketEndPointHeaderName();
	
	public String getHaveToBeMessageRequiredHeaderName();
	
	public String getContainerHeaderName();
	
	public String getTopicExchangeName();
}
