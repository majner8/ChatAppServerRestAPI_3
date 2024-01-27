package Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import chatAPP_CommontPart.Log4j2.Log4j2;
//import chatAPP_database.Chat.Messages.MessageEntity;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"chatAPP_database,ChatAPP_security"
		+ ",ChatAPP_RabitMQ","ChatAPP_WebSocket","ChatAPP_commontPart"})
@EntityScan(basePackages={"chatAPP_database"})
@EnableJpaRepositories(basePackages = {"chatAPP_database"})
@EnableAutoConfiguration
@EnableAspectJAutoProxy
/*
 * ,"ChatAPP_commontPart","chatAPP_DTO", "ChatAPP_HttpendPoint","ChatAPP_Chat",
 * "ChatAPP_WebSocket","ChatAPP_WebSocket_EndPoint"
 */
public class Main {

	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		Log4j2.log.info("Starting application");
	}


}