package ChatAPP_WebSocket_EndPoint.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;

import ChatAPP_WebSocket.Service.RabitMQService.RabitMQConsumingEndPointService;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;


public class WebSocketEndPointConfig implements WebSocketEndPointConfigInterface {
	
	public static final String StartConsumingPath=WebSocketEndPointPath.Config_StartConsumingPath;
	public static final String StopConsumingPath=WebSocketEndPointPath.Config_StopConsumingPath;

	@Autowired
	private RabitMQConsumingEndPointService service;
	//async have to be used, because metod do not use rabitMQ annotation

	@MessageMapping(WebSocketEndPointPath.Config_StartConsumingPath)
	@Override
	public void StartConsumingMessage(SimpMessageHeaderAccessor session,@DestinationVariable int offSetStart,@DestinationVariable int offSetEnd)  {
		this.service.StartConsuming(session,offSetStart,offSetEnd);
	}

	@Override
	public void StopConsumingMessage() {
		// TODO Auto-generated method stub
		
	}

	

}
