package ChatAPP_WebSocket_EndPoint.Configuration;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface WebSocketEndPointConfigInterface {


	public void StartConsumingMessage(SimpMessageHeaderAccessor session,int offSetStart,int offSetEnd);
	public void StopConsumingMessage();
}
