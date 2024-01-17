package ChatAPP_WebSocket.Service.RabitMQService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;

import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManager.CustomRabitMQQueue;
import ChatAPP_RabitMQ.Queue.RabbitMQQueueManagerInterface;
import chatAPP_CommontPart.AOP.RabitMQPropertiesAOP;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_DTO.DTO;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_database.Chat.Messages.MessageEntity;
import chatAPP_database.Chat.Messages.MessageRepositoryEntity;


public class RabitMQConsumingEndPointService implements RabitMqConsumingServiceInterface{


	
	@Autowired
	private RabbitMQQueueManagerInterface queueManager;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue simpSession;
	@Autowired
	private MessageRepositoryEntity messageRepo;
	@Autowired
	private RabitMQMessageProducerInterface amq;
	@Override
	public void StartConsuming(SimpMessageHeaderAccessor session,int offSetStart,int offSetEnd) {
		CustomRabitMQQueue que=this.queueManager.getDeviceQueueName();
		if(que.isWasQueueCreated()) {
			//have to start quick synchronization
			this.StartSynchronization(this.simpSession.getConnectionID(),this.simpSession.getSessionOwnerUserID(), offSetStart, offSetEnd);
		}
	//operation was sucesfull
	}

	@RabitMQPropertiesAOP(dtoClass = MessageDTO.class, 
			rabitMQPriory=5,
			getPath = "",
			haveToBeMessageRequired = false)
	private void StartSynchronization(String userWebSocketID,long UserID,int offSetStart,int offSetEnd) {
		if(Log4j2.log.isDebugEnabled()) {
			Log4j2.log.debug(Log4j2.MarkerLog.WebSocket.getMarker(),
					String.format("Loading messages from database, to process Quick Asyn Synchronization"
					+ System.lineSeparator()+"QueueID: %s"	
					+ System.lineSeparator()+"userID: %s"
					+ System.lineSeparator()+"offSetStart: %s"
					+ System.lineSeparator()+"offSetEnd: %s"
					, userWebSocketID,String.valueOf(UserID),
					String.valueOf(offSetStart),
					String.valueOf(offSetEnd)));
		}
		List<MessageEntity> mes=this.messageRepo.getQuickUserSynchronizationMessage(UserID, offSetStart, offSetEnd);
		List<DTO> dto=mes.stream().map((mesEnt)->{
			if(Log4j2.log.isTraceEnabled()) {
					Log4j2.log.trace(Log4j2.MarkerLog.WebSocket.getMarker(),
							String.format("Loading Quick SynchronizationMessage"
							+ System.lineSeparator()+"QueueID: %s"	
							+ System.lineSeparator()+"userID: %s"
							+ System.lineSeparator()+"offSetStart: %s"
							+ System.lineSeparator()+"offSetEnd: %s"
							+ System.lineSeparator()+"MessageID: %s"
							+ System.lineSeparator()+"chatID: %s"
							+ System.lineSeparator()+"order: %s"
							+ System.lineSeparator()+"timeStamp: %s"
							, userWebSocketID,UserID,
							offSetStart,
							offSetEnd,
							mesEnt.getMessageID(),
							mesEnt.getChatID(),
							mesEnt.getOrder(),
							mesEnt.getReceivedTime()
									));
			}
			return mesEnt.convertEntityToDTO();
		}).collect(Collectors.toList());
		Collections.reverse(dto);
			//user WebSocket ID is same as queue-unique per device
		this.amq.PushMessageToRabitMQ(dto, userWebSocketID);
		
	}
	@Override
	public void StopConsuming() {
		
	}

}
