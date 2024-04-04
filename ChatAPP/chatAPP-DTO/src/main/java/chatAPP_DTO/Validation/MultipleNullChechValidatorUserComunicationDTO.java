package chatAPP_DTO.Validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import chatAPP_DTO.User.UserComunicationDTO;






@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipleNullChechValidatorUserComunicationDTO.MultipleNullChechValidatorClass.class)
public @interface MultipleNullChechValidatorUserComunicationDTO {
    String message() default "Phone and phonePreflix have to be not null, or null both, also, email or phone have to be not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public static  class MultipleNullChechValidatorClass implements ConstraintValidator<MultipleNullChechValidatorUserComunicationDTO, UserComunicationDTO>{

		@Override
		public boolean isValid(UserComunicationDTO value, ConstraintValidatorContext context) {
			if(value.getEmail()==null&&(value.getPhone()==null||value.getPhonePreflix()==null))return false;
			if((value.getPhone()!=null&&value.getPhonePreflix()==null)||
				(value.getPhone()==null&&value.getPhonePreflix()!=null)	) return false;
			return true;
		}


    }
}
