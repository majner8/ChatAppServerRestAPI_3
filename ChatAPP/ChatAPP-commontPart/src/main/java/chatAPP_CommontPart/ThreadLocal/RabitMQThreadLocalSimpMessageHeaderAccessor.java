package chatAPP_CommontPart.ThreadLocal;


public interface RabitMQThreadLocalSimpMessageHeaderAccessor {
	public RabitMQConsumingMessageProperties getRabitMQMessageProperties();
	//public WebSocketEndPointAndMessageType getMessageType();
	public default int getRabitMQPriority() {
		return 0;
		//return this.getMessageType().getRabitMQPriority();
	}
	public default String getWebSocketEndPointPath() {
		return this.getRabitMQMessageProperties().getPath();
	}
	public default boolean isHaveToBeMessageReDeliver() {
		return this.getRabitMQMessageProperties().isHaveToBeMessageRequired();
	}
	public default Class<?> getDTOClass(){
		return this.getRabitMQMessageProperties().getDtoClass();
	}
}
