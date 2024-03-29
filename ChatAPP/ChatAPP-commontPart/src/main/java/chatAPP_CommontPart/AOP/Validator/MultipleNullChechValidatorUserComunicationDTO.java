package chatAPP_CommontPart.AOP.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipleNullChechValidatorUserComunicationDTO.MultipleNullChechValidatorClass.class)
public @interface MultipleNullChechValidatorUserComunicationDTO {
    String message() default "B and C must both be null or both be not null";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    String[]  multipleField();
    
    public static class MultipleNullChechValidatorClass implements ConstraintValidator<MultipleNullChechValidatorUserComunicationDTO, Object>{

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
}
