package chatAPP_CommontPart.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

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


	@Async("clientInboundChannelExecutor")
	@Around("@annotation(WebSocketThreadLocalSession)")
	public void MessageMappingTrigger(ProceedingJoinPoint joinPoint,MessageMapping WebSocketThreadLocalSession) throws Throwable {
		SimpMessageHeaderAccessor session=null;
		if(session==null) {
			for(Object O:joinPoint.getArgs()) {
				if(O instanceof SimpMessageHeaderAccessor) {
				session=(SimpMessageHeaderAccessor)O;
				break;
			}
			}
		}
		this.makeLog(joinPoint, session);

		if(session==null) {
			 joinPoint.proceed();
			return;
		}
		 this.WebSocketSession.setSimpMessageHeaderAccessor(session);
		try {
			 this.applyWSFilter.applyFilter(session.getDestination(),joinPoint.getArgs());
		 joinPoint.proceed();
		}
		finally {
			 this.WebSocketSession.clear();

		}
	}
	@Async("clientInboundChannelExecutor")
	@Around("@annotation(eventListener)")
	public void WebSocketConnectionEventListenerTrigger(ProceedingJoinPoint joinPoint,	EventListener eventListener) throws Throwable {

		StompHeaderAccessor session=null;
		for(Object O:joinPoint.getArgs()) {
			if(O instanceof AbstractSubProtocolEvent) {
				AbstractSubProtocolEvent ses=(AbstractSubProtocolEvent)O;
				session=StompHeaderAccessor.wrap(ses.getMessage());
				break;
			}
		}
		this.makeLog(joinPoint, session);
		if(session==null) {
			 joinPoint.proceed();
			return;
		}
		 this.WebSocketSession.setSimpMessageHeaderAccessor(session);
		try {
		 joinPoint.proceed();
		}
		finally {
			 this.WebSocketSession.clear();

		}
	}

	private void makeLog(ProceedingJoinPoint joinPoint,SimpMessageHeaderAccessor session) {
		if(Log4j2.log.isTraceEnabled()) {
			String evnokedBy=joinPoint.getClass().getName()+"."+joinPoint.getSignature().getName();

			String message=String.format("Running aspect metod MessageMappingAspect, Evoked by: %s",
			 evnokedBy);
			 Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(), message);
			 if(session==null) {
				 Log4j2.log.warn(Log4j2.MarkerLog.Aspect.getMarker(),evnokedBy+" Does not contain SimpMessageHeaderAccessor as parametr, AOP was skipped");
			 }
		}
	}

	}

