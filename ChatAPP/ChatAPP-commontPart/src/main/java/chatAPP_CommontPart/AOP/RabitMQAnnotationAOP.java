package chatAPP_CommontPart.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession.RabitMQConsumingMessageProperties;

@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime
@Target(ElementType.METHOD) // This annotation can only be applied to methods
public @interface RabitMQAnnotationAOP {

	String getPath();
	  int rabitMQPriory() default 20;
	  Class<?> dtoClass();
	  boolean haveToBeMessageRequired();
	  /**Set expiration for rabitMQ,
	   * otherwise will be used default one*/
	  String expiration() default "";
	  @Aspect
		@Component
		public static class rabitmqAOPClass{
		  @Autowired
		  private RabitMQThreadLocalSession rabitMQSession;
		  @Around("@annotation(aop)")
		  public Object AnnotationMetodCall(ProceedingJoinPoint joinPoint,RabitMQAnnotationAOP aop) throws Throwable  {
				
			  
			  if(Log4j2.log.isDebugEnabled()) {
					String evnokedBy=joinPoint.getClass().getName()+"."+joinPoint.getSignature().getName();

					 String message=String.format("Running aspect metod rabitMQAOP"+System.lineSeparator()
					 +"Evoked by: %s",
					 evnokedBy);
					 Log4j2.log.debug(Log4j2.MarkerLog.Aspect.getMarker(), message);
				 }
			RabitMQConsumingMessageProperties x=RabitMQConsumingMessageProperties.create(aop);
			this.rabitMQSession.setRabitMQConsumingMessageProperties(x);
					joinPoint.proceed();
				this.rabitMQSession.clear();
				return null;
					
				
		  };
		
			
	  }
			
}
