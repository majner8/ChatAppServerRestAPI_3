package ChatAPP.Test.DTODataIntegrity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_DTO.User.UserProfileDTO;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class UserDataIntegrityTest {

	
	@Autowired
	private Validator validator;
	
	@Test
	public void TestUserProfileDTO() {
		AtomicInteger ins=new AtomicInteger();
		ins.set(-1);
		String serName="serName";
		String LastName="LastName";

		String NickName="NickName";
		UserProfileDTO profile=new UserProfileDTO()
				.setSerName(serName+1)
				.setLastName(LastName+202)
				.setNickName(NickName.repeat(10));
		Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),"Start validating, number : "+ins.getAndIncrement());

		this.validator.validate(profile).forEach(x->{
			Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),x.getInvalidValue());

			if(x.getInvalidValue().equals(profile.getLastName())||
					x.getInvalidValue().equals(profile.getSerName())
					||x.getInvalidValue().equals(profile.getNickName())) assertTrue(true);
			else {
				fail();
			}
		});
		
		profile.setLastName(LastName)
		.setSerName(serName)
		.setNickName(NickName+1);
		Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),"Start validating, number : "+ins.getAndIncrement());

		this.validator.validate(profile).forEach(x->{
			Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),x.getInvalidValue());
			assertTrue(x.getInvalidValue().equals(profile.getNickName()));
		});
		profile.setNickName(NickName);
		Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),"Start validating, number : "+ins.getAndIncrement());

		assertTrue(this.validator.validate(profile).isEmpty());
		
	}
	@Test
	public void testUserAuthorizationDTO() {
		
	}
}
