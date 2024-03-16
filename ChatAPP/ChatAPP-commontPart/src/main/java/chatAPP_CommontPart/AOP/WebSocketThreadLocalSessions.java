package chatAPP_CommontPart.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;


@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime
@Target({ElementType.METHOD}) // This annotation can only be applied to methods
public @interface WebSocketThreadLocalSessions {

	@Aspect
	@Component
	public static class WebSocketAnnotationClass{
		@Autowired
		private WebSocketThreadLocalSessionInterface WebSocketSession;
		 @Around("@annotation(WebSocketThreadLocalSession)")
		public void calledMetod(ProceedingJoinPoint joinPoint,WebSocketThreadLocalSessions WebSocketThreadLocalSession) throws Throwable {
			String evnokedBy=joinPoint.getClass().getName()+"."+joinPoint.getSignature().getName();
			SimpMessageHeaderAccessor session=null;
			for(Object O:joinPoint.getArgs()) {
				if(O instanceof SimpMessageHeaderAccessor) {
					session=(SimpMessageHeaderAccessor)O;
					break;
				}
			}
			if(Log4j2.log.isTraceEnabled()) {
			
				String message=String.format("Running aspect metod WebSocketThreadLocalSession, Evoked by: %s",	
				 evnokedBy);
				 Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(), message);
				 if(session==null) {
					 Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(),evnokedBy+" Does not contain SimpMessageHeaderAccessor as parametr, AOP was skipped");
				 }
			}
			
		/*	SimpMessageHeaderAccessor ses = null;
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
			 }*/
			if(session==null) {
				 joinPoint.proceed();			 
				return;
			}
			 this.WebSocketSession.setSimpMessageHeaderAccessor(session);
			 joinPoint.proceed();			 
			 this.WebSocketSession.clear();
			
		}
		
	}
}
