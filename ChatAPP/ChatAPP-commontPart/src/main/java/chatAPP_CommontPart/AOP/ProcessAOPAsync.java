package chatAPP_CommontPart.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;



public interface ProcessAOPAsync {

	private void processItAsync(ProceedingJoinPoint joinPoint,RabitMQPropertiesAOP aop); 
	
}
