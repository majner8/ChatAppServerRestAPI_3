package chatAPP_CommontPart.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.MessageMapping;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Security.applyWebSocketFilter;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

@Aspect
@Component
public class MessageMappingAspect {

	@Autowired
	private applyWebSocketFilter applyWSFilter;
	@Autowired
	private WebSocketThreadLocalSessionInterface WebSocketSession;
	@Async
	@Around("@annotation(WebSocketThreadLocalSession)")
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
		
			String message=String.format("Running aspect metod MessageMappingAspect, Evoked by: %s",	
			 evnokedBy);
			 Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(), message);
			 if(session==null) {
				 Log4j2.log.warn(Log4j2.MarkerLog.Aspect.getMarker(),evnokedBy+" Does not contain SimpMessageHeaderAccessor as parametr, AOP was skipped");
			 }
		}	
		if(session==null) {
			 joinPoint.proceed();			 
			return;
		}
		 this.WebSocketSession.setSimpMessageHeaderAccessor(session);
		 try {
			 this.applyWSFilter.applyFilter(session.getDestination());
		 }
		 catch(Exception e) {
			 this.WebSocketSession.clear();
		 }
		 
		 
		try {
		 joinPoint.proceed();
		}
		finally {
			 this.WebSocketSession.clear();

		}
			}
	
	
}

