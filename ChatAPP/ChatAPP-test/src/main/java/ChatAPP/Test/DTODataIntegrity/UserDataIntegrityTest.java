package ChatAPP.Test.DTODataIntegrity;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import chatAPP_DTO.User.UserAuthPasswordDTO;
import chatAPP_DTO.User.UserAuthorizationDTO;
import chatAPP_DTO.User.UserProfileDTO;


@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class UserDataIntegrityTest {

	
	@Autowired
	private Validator validator;
	
	@Test
	public void TestUserProfileDTO() {
		String serName="serName";
		String LastName="LastName";

		String NickName="NickName";
		UserProfileDTO profile=new UserProfileDTO()
				.setSerName(serName+1)
				.setLastName(LastName+202)
				.setNickName(NickName.repeat(10));
	

		Set<ConstraintViolation<UserProfileDTO>> val=	this.validator.validate(profile);
        assertEquals(3,val.size());
		val
		.forEach(x->{
			if(x.getInvalidValue().equals(profile.getLastName())||
					x.getInvalidValue().equals(profile.getSerName())
					||x.getInvalidValue().equals(profile.getNickName())) assertTrue(true);
			else {
				fail();
			}
		});		
		profile.setLastName(null)
		.setSerName(null)
		.setNickName(null);
		
		 val=	this.validator.validate(profile);
	        assertEquals(3,val.size());
	    

		 
			val
			.forEach(x->{
				if(x.getInvalidValue()==null||
						x.getInvalidValue()==null
						||x.getInvalidValue()==null) assertTrue(true);
				else {
					fail();
				}
			});
			profile.setLastName("")
			.setSerName("")
			.setNickName("");
			
			 val=	this.validator.validate(profile);
				assertTrue(val.size()==3);
				val
				.forEach(x->{
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
		
		 val=this.validator.validate(profile);
	        assertEquals(false,val.isEmpty());

		 val.forEach(x->{
			 assertEquals(profile.getNickName(),x.getInvalidValue());
		});
		 profile.setNickName(NickName);
		 val=this.validator.validate(profile);
	        assertEquals(true,val.isEmpty());

		
	}
	@Test
	public void testUserAuthorizationDTO() {
		UserAuthorizationDTO userAut=new UserAuthorizationDTO();
		Set<ConstraintViolation<UserAuthorizationDTO>> val=this.validator.validate(userAut);
		assertEquals(2,val.size());
		val.forEach((x)->{
			if(x.getInvalidValue()!=null)fail();
			
		});
		//set password
		UserAuthPasswordDTO pass=new UserAuthPasswordDTO();
		userAut.setPassword(pass);
		val=this.validator.validate(userAut);
		assertEquals(3,val.size());
		val.forEach((x)->{
			if(x.getInvalidValue()!=null)fail();
			
		});
		
		
	}
	
	private void makeValidation(Object toValidate,Object... expectValue) {
		this.validator
	}
}
