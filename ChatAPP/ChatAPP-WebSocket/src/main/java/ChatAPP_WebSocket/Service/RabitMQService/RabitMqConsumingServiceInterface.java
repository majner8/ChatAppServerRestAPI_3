package ChatAPP_WebSocket.Service.RabitMQService;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface RabitMqConsumingServiceInterface {

	public void StartConsuming(SimpMessageHeaderAccessor session,int offSetStart,int offSetEnd);

	public void StopConsuming();
}
