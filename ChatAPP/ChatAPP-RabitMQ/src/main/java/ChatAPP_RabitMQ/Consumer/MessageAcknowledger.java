package ChatAPP_RabitMQ.Consumer;

import java.io.IOException;

public interface MessageAcknowledger {

	public void AckMessage(String sessionID,String messageID) ;
	public void NackMessage(String sessionID,String messageID) ;

	
}
