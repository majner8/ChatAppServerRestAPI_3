package chatAPP_CommontPart.ThreadLocal;

public class RabitMQConsumingMessageProperties {

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
