package chatAPP_CommontPart.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;


@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime
@Target({ElementType.TYPE,ElementType.METHOD}) // This annotation can only be applied to methods
public @interface WebSocketThreadLocalSession {

	
	public static class WebSocketAnnotationClass{
		@Autowired
		private WebSocketThreadLocalSessionInterface WebSocketSession;
		//@Around("execution(public void(..)) && @annotation(WebSocketThreadLocalSession)")
		public void calledMetod(ProceedingJoinPoint joinPoint) throws Throwable {
			String evnokedBy=joinPoint.getClass().getName()+"."+joinPoint.getSignature().getName();

			if(Log4j2.log.isTraceEnabled()) {

				String message=String.format("Running aspect metod rabitMQAOP, Evoked by: %s is running with these metod"+System.lineSeparator()
				 +"endPoint path: %s"+System.lineSeparator(),
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
				 Log4j2.log.warn(Log4j2.MarkerLog.Aspect.getMarker(), "Running aspect metod WebSocketThreadLocalSession cannot be process properly, because SimpMessageHeaderAccessor is null. Saving WebSocket session was skipped"
						 +System.lineSeparator()+ " Metod evoked by: "+evnokedBy);
				 joinPoint.proceed();			 
				 
				 return;
			 }
			 this.WebSocketSession.setSimpMessageHeaderAccessor(ses);
			 joinPoint.proceed();			 
			 this.WebSocketSession.clear();
			
		}
		
	}
}
