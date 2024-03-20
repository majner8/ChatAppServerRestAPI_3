package ChatAPP_RabitMQ.Listener;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public interface SimpleMessageListenerContainerManager {

	public SimpleMessageListenerContainer createSimpleMessageListenerContainer(String userdeviceid);
	
	@Component
	public static class SimpleMessageListenerContainerManagerClass implements SimpleMessageListenerContainerManager{

	    @Autowired
	    private ConnectionFactory connectionFactory;
	    @Autowired
	    @Qualifier("customChannelAwareMessageListener")
	    private ChannelAwareMessageListener listener;

		@Override
		public SimpleMessageListenerContainer createSimpleMessageListenerContainer(String userdeviceid) {
			// TODO Auto-generated method stub
			
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
			container.setConnectionFactory(connectionFactory);
		    container.setQueueNames(userdeviceid);
		    container.setMessageListener(listener);
		    return container;
		}
		
	}
	
}
