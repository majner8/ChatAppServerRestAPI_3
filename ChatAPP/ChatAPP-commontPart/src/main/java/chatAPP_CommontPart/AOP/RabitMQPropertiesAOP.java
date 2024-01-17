package chatAPP_CommontPart.AOP;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.RabitMQConsumingMessageProperties;
import chatAPP_CommontPart.ThreadLocal.ThreadLocalSessionSimpMessageHeaderAccessor;

@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime
@Target(ElementType.METHOD) // This annotation can only be applied to methods
public @interface RabitMQPropertiesAOP {
	  String getPath();
	  int rabitMQPriory() default 20;
	  Class<?> dtoClass();
	  boolean haveToBeMessageRequired();
	  /**Set expiration for rabitMQ,
	   * otherwise will be used default one*/
	  String expiration() default "";
		@Aspect
		@Component
		public static class rabitmqAOP{
			
		@Autowired
		private ProcessAOPAsync async;
		@Autowired
		private ThreadLocalSessionSimpMessageHeaderAccessor manipulation;
		@Pointcut("execution(void *.*(..)) && @annotation(RabitMQPropertiesAOP)")
		public void AnnotationMetodCall() {};
		
		@Around("annotationMethodCall()")
		public void ProcessMessageMapping(ProceedingJoinPoint joinPoint,RabitMQPropertiesAOP aop)throws Throwable {
			if(Log4j2.log.isTraceEnabled()) {
				String evnokedBy=joinPoint.getClass().getName()+"."+joinPoint.getSignature().getName();

				String message=String.format("Running aspect metod rabitMQAOP, Evoked by: %s is running with these metod"+System.lineSeparator()
				 +"endPoint path: %s"+System.lineSeparator(),
				 evnokedBy);
				 Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(), message);
			 	
			}
			final ProceedingJoinPoint x=joinPoint;
			final RabitMQPropertiesAOP y=aop;
			Runnable task=()->{
				this.processItAsync(x, y);
			};
			this.async.ProcessAsync(task);
		}
		private void processItAsync(ProceedingJoinPoint joinPoint,RabitMQPropertiesAOP aop) {
			String evnokedBy=joinPoint.getClass().getName()+"."+joinPoint.getSignature().getName();
			 if(Log4j2.log.isDebugEnabled()) {
				 
				 String message=String.format("Running aspect metod rabitMQAOP"+System.lineSeparator()
				 +"Evoked by: %s",
				 evnokedBy);
				 Log4j2.log.debug(Log4j2.MarkerLog.Aspect.getMarker(), message);
			 }
			 SimpMessageHeaderAccessor ses = null;
			 for(Object x:joinPoint.getArgs()) {
			 		if(x instanceof SimpMessageHeaderAccessor) {
			 			ses=(SimpMessageHeaderAccessor)x;
			 		}
			 	}
			 if(ses==null) {
				 Log4j2.log.error(Log4j2.MarkerLog.Aspect.getMarker(), "Running aspect metod rabitMQAOP cannot be process, SimpMessageHeaderAccessor cannot be null"
				 		+System.lineSeparator()+ " Metod evoked by: "+evnokedBy);
							 return;
			 }
			 RabitMQConsumingMessageProperties pr=new RabitMQConsumingMessageProperties(aop.getPath(),aop.rabitMQPriory(),aop.dtoClass(),aop.haveToBeMessageRequired());
			 this.manipulation.setSimpMessageHeaderAccessor(ses, pr);
			try {
				joinPoint.proceed();
			} catch (Throwable e) {
				 Log4j2.log.error(Log4j2.MarkerLog.Aspect.getMarker(), "Running aspect metod rabitMQAOP cannot be process due to "+e
					 		+System.lineSeparator()+ " Metod evoked by: "+evnokedBy);
											}
			finally {
				this.manipulation.clear();
			}
		}
	}
}
