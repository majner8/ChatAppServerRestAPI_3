package chatAPP_CommontPart.ThreadLocal;

import org.springframework.stereotype.Component;

import chatAPP_CommontPart.AOP.RabitMQAnnotationAOP;
import chatAPP_CommontPart.Log4j2.Log4j2;

public interface RabitMQThreadLocalSession {

	public static ThreadLocal<RabitMQConsumingMessageProperties> rabitMQprop=
			new ThreadLocal<>();

	public default void setRabitMQConsumingMessageProperties(RabitMQConsumingMessageProperties value) {
		Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(),"Setting RabbitMQTreadLocalSession");
		RabitMQThreadLocalSession.rabitMQprop.set(value);
	}
	public default void clear() {
		Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(),"Removing RabbitMQTreadLocalSession");

		RabitMQThreadLocalSession.rabitMQprop.remove();
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
	@Component
	public static class classJustForImplementationRabitMQThreadLocalSession implements RabitMQThreadLocalSession{}

	@Component
	public static class classJustForImplementationRabitMQThreadLocalSessionValue implements RabitMQThreadLocalSession.RabitMQThreadLocalSessionValue{

	}

	public static class RabitMQConsumingMessageProperties {

	private String path;
	private int rabitMQPriority;
	private Class<?>dtoClass;
	private boolean haveToBeMessageRequired;

	public RabitMQConsumingMessageProperties(String path, int rabitMQPriority, Class<?> dtoClass,
			boolean haveToBeMessageRequired) {
		this.path = path;
		this.rabitMQPriority = rabitMQPriority;
		this.dtoClass = dtoClass;
		this.haveToBeMessageRequired = haveToBeMessageRequired;
	}
	public static RabitMQConsumingMessageProperties create(RabitMQAnnotationAOP aop) {
		return new RabitMQConsumingMessageProperties(aop.getPath(),aop.rabitMQPriory(),aop.dtoClass(),aop.haveToBeMessageRequired());

	}
	public String getPath() {
		return path;
	}
	public int getRabitMQPriority() {
		return rabitMQPriority;
	}
	public Class<?> getDtoClass() {
		return dtoClass;
	}
	public boolean isHaveToBeMessageRequired() {
		return haveToBeMessageRequired;
	}

}

}
