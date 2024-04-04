package ChatAPP_RabitMQ.Consumer;

public interface MessageAcknowledger {

	public void AckMessage(String sessionID,String messageID) ;
	public void NackMessage(String sessionID,String messageID) ;


}
