package chatAPP_CommontPart.ThreadLocal;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import chatAPP_CommontPart.Security.CustomUserDetailsInterface;

public interface WebSocketThreadLocalSessionInterface {

	//public static final ThreadLocal<Pair<SimpMessageHeaderAccessor,CustomUserDetailsInterface>> wsSession=new ThreadLocal<Pair<SimpMessageHeaderAccessor,CustomUserDetailsInterface>>();



	/**Metod clear current ThreadLocalValue */
	public void clear();
	//public void setSimpMessageHeaderAccessor(SimpMessageHeaderAccessor par,RabitMQConsumingMessageProperties mesType);
	public  void setSimpMessageHeaderAccessor(SimpMessageHeaderAccessor session);

	public static interface WebSocketThreadLocalSessionValue{
		public CustomUserDetailsInterface getCustomUserDetails();

		public default long getSessionOwnerUserID() {
			return this.getCustomUserDetails().getUserID();
		}

		public SimpMessageHeaderAccessor getSimpMessageHeaderAccessor();
		/**Metod return unique ID for currect connected device
		 * ID is contain deviceID+UserID */
		public  String getConnectionID();

		public boolean IsUserConsumingNow();

		public  SimpleMessageListenerContainer getSimpleMessageListenerContainer();



		/**Metod set to attribute of session
		 * @return false-if operation could not be sucesfull, because SimpleMessageListenerContainer instance already exist
		 * otherwise true */
		public boolean setSimpleMessageListenerContainer(SimpleMessageListenerContainer container);
	}


}
