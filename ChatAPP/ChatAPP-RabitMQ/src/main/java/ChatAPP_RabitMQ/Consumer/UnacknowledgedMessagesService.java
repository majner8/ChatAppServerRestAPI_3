package ChatAPP_RabitMQ.Consumer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Component
@Order(1)
public class UnacknowledgedMessagesService implements UnAcknowledgeMessageListManager,MessageAcknowledger,RabbitMQConsumerManager {

	private final Map<String,Map<String,Messages>>listOfMessage=
			Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public void startConsume(String userdeviceID) {
		this.listOfMessage.put(userdeviceID, Collections.synchronizedMap(new HashMap<String,Messages>()));
	}

	@Override
	public void stopConsume(String userdeviceID, boolean DoesDeviceDisconect)  {
		Map<String,Messages> mes;
		//have to nack all messages
		synchronized(this.listOfMessage) {
		 mes=this.listOfMessage.get(userdeviceID);
		 if(mes==null) {
			 Log4j2.log.warn(Log4j2.MarkerLog.RabitMQ.getMarker(),
					 String.format("client connection, ID: %s was end, but Appropriate RabitMQ object has been removed before. ",userdeviceID ));
			 return;
		 }
		 this.listOfMessage.remove(userdeviceID);		
		 }
		
		synchronized(mes) {
			Collection<Messages> x=mes.values();
			for(Messages V:x) {
				if(V.isWasMessageAlreadyAcknowledged()) {
					continue;
				}
				try {
					V.getRabitChannel().basicNack(V.getDeliveryTag(), false, V.isShouldBeMessageRequiredAfterExpiration());
				} catch (IOException e) {
					Log4j2.log.error(Log4j2.MarkerLog.RabitMQ.getMarker(),String.format("Error during NackMessage, ID: %s deliveryTag: %s %s %s", V.getMessageID(),V.getDeliveryTag(),System.lineSeparator(),e));
					return;
				}
				
			}
			
			mes.forEach((K,V)->{
				if(V.isWasMessageAlreadyAcknowledged()) {
					return;
				}
				try {
					V.getRabitChannel().basicNack(V.getDeliveryTag(), false, V.isShouldBeMessageRequiredAfterExpiration());
				} catch (IOException e) {
					
				}
			});
		}
		
	}
		public static final class Messages{
			private  String messageID;
			private long deliveryTag;
			private Channel rabitChannel;
			private long timeStamp;
			private boolean shouldBeMessageRequiredAfterExpiration;
			private boolean wasMessageAlreadyAcknowledged=false;
			
			
			public Messages(String messageID, long deliveryTag, Channel rabitChannel, long timeStamp,
					boolean shouldBeMessageRequiredAfterExpiration, boolean wasMessageAlreadyAcknowledged) {
				this.messageID = messageID;
				this.deliveryTag = deliveryTag;
				this.rabitChannel = rabitChannel;
				this.timeStamp = timeStamp;
				this.shouldBeMessageRequiredAfterExpiration = shouldBeMessageRequiredAfterExpiration;
				this.wasMessageAlreadyAcknowledged = wasMessageAlreadyAcknowledged;
			}
			public void setWasMessageAlreadyAcknowledged(boolean wasMessageAlreadyAcknowledged) {
				this.wasMessageAlreadyAcknowledged = wasMessageAlreadyAcknowledged;
			}
			public String getMessageID() {
				return messageID;
			}
			public long getDeliveryTag() {
				return deliveryTag;
			}
			public Channel getRabitChannel() {
				return rabitChannel;
			}
			public long getTimeStamp() {
				return timeStamp;
			}
			public boolean isShouldBeMessageRequiredAfterExpiration() {
				return shouldBeMessageRequiredAfterExpiration;
			}
			public boolean isWasMessageAlreadyAcknowledged() {
				return wasMessageAlreadyAcknowledged;
			}
			
		}
	
	
	@Override
	public void AckMessage(String sessionID, String messageID) {
		
		Map<String,Messages> message=this.listOfMessage.get(sessionID);
		Messages mes=message.get(messageID);
		if(mes==null) {
			//messageWas nackBefore
			
			return;
		}
		synchronized(mes) {
			if(mes.wasMessageAlreadyAcknowledged) {
				//message was ackBefore
				return;
			}			
			try {
				mes.getRabitChannel().basicAck(mes.getDeliveryTag(), false);
				}
				catch(IOException e) {
					Log4j2.log.error(Log4j2.MarkerLog.RabitMQ.getMarker(),String.format("Error during AckMessage, ID: %s deliveryTag: %s %s %s", mes.getMessageID(),mes.getDeliveryTag(),System.lineSeparator(),e));
				}
				finally {
					message.remove(messageID);
					mes.setWasMessageAlreadyAcknowledged(true);
				}
		}
		
	}

	@Override
	public void NackMessage(String sessionID, String messageID) {
		Map<String,Messages> message=this.listOfMessage.get(sessionID);
		Messages mes=message.get(messageID);
		
		if(mes==null) {
			//messageWas nackBefore
			
			return;
		}
		synchronized(mes) {
			if(mes.wasMessageAlreadyAcknowledged) {
				//message was ackBefore
				return;
			}
			try {
			mes.getRabitChannel().basicNack(mes.getDeliveryTag(), false,mes.isShouldBeMessageRequiredAfterExpiration());
			}
			catch(IOException e) {
				Log4j2.log.error(Log4j2.MarkerLog.RabitMQ.getMarker(),String.format("Error during NackMessage, ID: %s deliveryTag: %s %s %s", mes.getMessageID(),mes.getDeliveryTag(),System.lineSeparator(),e));
			}
			finally {
				message.remove(messageID);
				mes.setWasMessageAlreadyAcknowledged(true);
			}
		}
	}

	
	@Override
	public void addMessageToList(String queueNameId, String messageID, Channel channel, long deliveryTag,
			boolean haveToBeMessageRequired) {
		
		
	}



}
