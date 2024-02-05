package ChatAPP_RabitMQ.Consumer;


public interface RabbitMQMessageRelayInterface {

	public void SendConsumedMessage(String webSocketEndPointPath,String messageID,String  message,String recipientID);
	
	//public void MessageTimeoutExpired(String recipientID,String messageID);
}
