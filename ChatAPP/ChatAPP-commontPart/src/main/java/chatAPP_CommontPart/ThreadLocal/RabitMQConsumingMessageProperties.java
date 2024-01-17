package chatAPP_CommontPart.ThreadLocal;

import chatAPP_CommontPart.AOP.RabitMQAnnotationAOP;
import chatAPP_CommontPart.Log4j2.Log4j2;

public class RabitMQConsumingMessageProperties {

	private String path;
	private int rabitMQPriority;
	private Class<?>dtoClass;
	private boolean haveToBeMessageRequired;
	
	private RabitMQConsumingMessageProperties(String path, int rabitMQPriority, Class<?> dtoClass,
			boolean haveToBeMessageRequired) {
		this.path = path;
		this.rabitMQPriority = rabitMQPriority;
		this.dtoClass = dtoClass;
		this.haveToBeMessageRequired = haveToBeMessageRequired;
		if(Log4j2.log.isTraceEnabled()) {
			String message=String.format("I created rabitMQConsumingMessage Properties with value"+System.lineSeparator()+
					"path: %s"+System.lineSeparator()+
					"rabitMQpriory %d"+System.lineSeparator()+
					"dtoClass name: %s"+System.lineSeparator()+
					"haveTobeMessageRequired: %s"+System.lineSeparator()
					, this.path,this.rabitMQPriority,this.dtoClass.getPackageName()+this.dtoClass.getName(),
					this.haveToBeMessageRequired);
			Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(),message);
		}
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
