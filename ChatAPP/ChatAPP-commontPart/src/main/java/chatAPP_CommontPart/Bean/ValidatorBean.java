package chatAPP_CommontPart.Bean;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorBean {

	@Bean 
	public Validator create() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}
}
