package chatAPP_CommontPart.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime
@Target({ElementType.FIELD,ElementType.PARAMETER}) // This annotation can only be applied to methods
public @interface Validate {

	@Aspect
	@Component
	public static class ValidateClass{
		@Autowired
		private Validator validator;
		
		
		
		@Before("@annotation(chatAPP_CommontPart.AOP.Validate)")
		public void validate(JoinPoint joinPoint) {
			if(Log4j2.log.isTraceEnabled()) {
				Log4j2.log.trace(Log4j2.MarkerLog.Aspect.getMarker(),"AOP validate was trigger");
			}
			Set<ConstraintViolation<Object>> violations = validator.validate(joinPoint.getTarget());
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            
		}
	}
}
