package ChatAPP_Security.BCrypt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptEncoderBean {

	@Bean
	public BCryptPasswordEncoder defineBean() {
        return new BCryptPasswordEncoder();

	}

}
