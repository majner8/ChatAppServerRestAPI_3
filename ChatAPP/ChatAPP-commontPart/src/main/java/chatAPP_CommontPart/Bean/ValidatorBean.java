package chatAPP_CommontPart.Bean;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Configuration
public class ValidatorBean {

	@Bean
	public Validator create() {
		return new ValidationClass();
	}


	public static class ValidationClass implements Validator{

		private final Validator validator;
		public ValidationClass() {
			this.validator=Validation.buildDefaultValidatorFactory().getValidator();
		}
		@Override
		public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
			// TODO Auto-generated method stub
			Set<ConstraintViolation<T>>set=
			this.validator.validate(object, groups)	;

			if(Log4j2.log.isTraceEnabled()) {
				set.forEach((x)->{
					Log4j2.log.trace(Log4j2.MarkerLog.Validation.getMarker(),
							String.format("Validation fail Beean name: %s path to value %s invalid value: %s",
									x.getRootBeanClass().getSimpleName(),x.getPropertyPath().toString(),x.getInvalidValue())
							);
				});
			}
			return set;
		}

		@Override
		public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
			// TODO Auto-generated method stub
			return this.validator.validateProperty(object, propertyName, groups);
		}

		@Override
		public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
				Class<?>... groups) {
			// TODO Auto-generated method stub
			return this.validator.validateValue(beanType, propertyName, value, groups);
		}

		@Override
		public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
			// TODO Auto-generated method stub
			return this.validator.getConstraintsForClass(clazz);
		}

		@Override
		public <T> T unwrap(Class<T> type) {
			// TODO Auto-generated method stub
			return this.validator.unwrap(type);
		}

		@Override
		public ExecutableValidator forExecutables() {
			// TODO Auto-generated method stub
			return this.validator.forExecutables();
		}

	}
}
