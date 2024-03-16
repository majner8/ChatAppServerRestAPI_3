package chatAPP_CommontPart.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

@Aspect
@Component
public class MessageMappingAspect {

	@Autowired
	private WebSocketThreadLocalSessionInterface WebSocketSession;
	//@Around("execution(public void(..)) && @annotation(WebSocketThreadLocalSession)")
	@Async
	@Around("@annotation(MessageMapping)")
	public void calledMetod(ProceedingJoinPoint joinPoint,MessageMapping WebSocketThreadLocalSession) throws Throwable {
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
		if(session==null) {
			 joinPoint.proceed();			 
			return;
		}
		 this.WebSocketSession.setSimpMessageHeaderAccessor(session);
		 joinPoint.proceed();			 
		 this.WebSocketSession.clear();
		
	}
	
}

