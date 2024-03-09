package chatAPP_CommontPart.Bean;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class Jackson2JsonMessageConverterBean {
	
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		ObjectMapper mapper = new ObjectMapper()
	            .registerModule(new JavaTimeModule()) // Register JSR310 (Java 8 Date/Time) module
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Optional: to use ISO-8601 format
	        return new Jackson2JsonMessageConverter(mapper);	}
	        
}
