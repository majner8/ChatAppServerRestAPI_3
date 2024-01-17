package chatAPP_CommontPart.ThreadLocal;

public interface RabitMQThreadLocalSession {

	public static ThreadLocal<RabitMQConsumingMessageProperties> rabitMQprop=
			new ThreadLocal<RabitMQConsumingMessageProperties>();
	
	public default void setRabitMQConsumingMessageProperties(RabitMQConsumingMessageProperties value) {
		this.rabitMQprop.set(value);
	}
	public default void clear() {
		this.rabitMQprop.remove();
	}
	
	public static interface RabitMQThreadLocalSessionValue{
		public default int getRabitMQPriority() {
			return 0;
			//return this.getMessageType().getRabitMQPriority();
		}
		public default String getWebSocketEndPointPath() {
			return RabitMQThreadLocalSession.rabitMQprop.get().getPath();
		}
		public default boolean isHaveToBeMessageReDeliver() {
			return RabitMQThreadLocalSession.rabitMQprop.get().isHaveToBeMessageRequired();
		}
		public default Class<?> getDTOClass(){
			return RabitMQThreadLocalSession.rabitMQprop.get().getDtoClass();
		}
	}
	
	
}
