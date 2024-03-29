package ChatAPP.Test.DTODataIntegrity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Valid;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import chatAPP_CommontPart.AOP.Validate;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_DTO.User.UserProfileDTO;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class UserDataIntegrityTest {

	
	@Autowired
	private Validator validator;
	
	@Test
	public void TestUserProfileDTO() {
		
		UserProfileDTO profile=new UserProfileDTO()
				.setSerName("asdsa")
				.setLastName("asddas")
				.setNickName("asddessa".repeat(10));

		if(!this.validator.validate(profile).stream()
				.map(value->{
					Log4j2.log.error(Log4j2.MarkerLog.Test.getMarker(),"Error during validation "+value);
					return value;
				})
				.toList()
				.isEmpty()) assertTrue(false);
		
		assertTrue(true);
	}
	
	
}
