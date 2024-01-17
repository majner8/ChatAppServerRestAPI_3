package chatAPP_CommontPart.ThreadLocal;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;




public interface ThreadLocalSimpMessageHeaderAccessor {

	public long getSessionOwnerUserID();
	public SimpMessageHeaderAccessor getSimpMessageHeaderAccessor();
	public String getProcessingWebSocketDestination();
	/**Metod return unique ID for currect connected device
	 * ID is contain deviceID+UserID */
	public default String getConnectionID() 
	{return this.getSimpMessageHeaderAccessor().getUser().getName();}
	public boolean IsUserConsumingNow();
	public SimpleMessageListenerContainer getSimpleMessageListenerContainer();
	
	
	
	/**Metod set to attribute of session
	 * @return false-if operation could not be sucesfull, because SimpleMessageListenerContainer instance already exist
	 * otherwise true */
	public boolean setSimpleMessageListenerContainer(SimpleMessageListenerContainer container);
}

