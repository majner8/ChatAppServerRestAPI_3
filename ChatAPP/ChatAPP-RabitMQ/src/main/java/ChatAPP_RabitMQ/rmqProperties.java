package ChatAPP_RabitMQ;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class rmqProperties implements RabitMQProperties {

	@Value("${rabbitmq.UnAcknowledgeMessageTimeout}")
	private Duration unTimeout;
	@Value("${rabbitmq.HaveToBeMessageRequiredHeaderName}")
	private String haveToBeRequiredHeaderName;
	@Value("${rabbitmq.ContainerHeaderName}")
	private String ContainerHeaderName;
	@Value("${rabbitmq.WebSocketEndPointHeaderName}")
	private String WebSocketEndPointHeaderName;
	

	@Override
	public Duration getUnacknowledgedMessageTimeout() {
		// TODO Auto-generated method stub
		return this.unTimeout;
	}

	
	@Override
	public String getHaveToBeMessageRequiredHeaderName() {
		// TODO Auto-generated method stub
		return this.haveToBeRequiredHeaderName;
	}

	@Override
	public String getContainerHeaderName() {
		// TODO Auto-generated method stub
		return this.ContainerHeaderName;
	}


	@Override
	public String getMessagePropertiesWebSocketEndPointHeaderName() {
		// TODO Auto-generated method stub
		return this.WebSocketEndPointHeaderName;
	}

}
