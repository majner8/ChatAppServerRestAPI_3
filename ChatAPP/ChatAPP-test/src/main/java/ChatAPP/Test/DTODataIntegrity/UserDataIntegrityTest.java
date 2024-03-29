package ChatAPP.Test.DTODataIntegrity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_DTO.User.UserAuthPasswordDTOInput;
import chatAPP_DTO.User.UserAuthorizationDTO;
import chatAPP_DTO.User.UserComunicationDTO;
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
	
		this.makeValidation(profile, profile.getLastName(),profile.getNickName(),profile.getSerName());
		profile.setLastName(null)
		.setSerName(null)
		.setNickName(null);
		this.makeValidation(profile, null,null,null);
		profile.setLastName("")
		.setSerName("")
		.setNickName("");
		this.makeValidation(profile, profile.getLastName(),profile.getNickName(),profile.getSerName());
			
		profile.setLastName(LastName)
		.setSerName(serName)
		.setNickName(NickName+1);
		this.makeValidation(profile, profile.getNickName());
		
		profile.setNickName(NickName);
		this.makeValidation(profile);		
	}
	@Test
	public void testUserAuthorizationDTO() {
		UserAuthorizationDTO userAut=new UserAuthorizationDTO();
		this.makeValidation(userAut, null,null);
		//set password
		UserAuthPasswordDTOInput pass=new UserAuthPasswordDTOInput();
		userAut.setPassword(pass);
		//because UserAuthPass has just one field
		this.makeValidation(userAut, null,null);

		pass.setPassword("As");
		userAut.setPassword(pass);
		this.makeValidation(userAut, userAut.getPassword().getPassword(),null);
	
		pass.setPassword("As1");
		userAut.setPassword(pass);
		this.makeValidation(userAut,null);
		
		UserComunicationDTO com=new UserComunicationDTO();
		userAut.setProfile(com);
		
		this.makeValidation(userAut,null,null,null);
		
		com.setEmail("Tonik");
		userAut.setProfile(com);
		this.makeValidation(userAut, null,null,com.getEmail());
		com.setEmail("tonik.120@gmail.cmo");
		userAut.setProfile(com);
		this.makeValidation(userAut, null,null);
		
		com.setPhonePreflix("+333");
		userAut.setProfile(com);
		this.makeValidation(userAut, null,com.getPhonePreflix());
		
		com.setPhonePreflix("3333");
		userAut.setProfile(com);
		this.makeValidation(userAut, null,com.getPhonePreflix());
		com.setPhonePreflix("33+");
		userAut.setProfile(com);
		this.makeValidation(userAut, null,com.getPhonePreflix());
		
		
		 com.setPhonePreflix("333");
		userAut.setProfile(com);
		this.makeValidation(userAut, null);
		
		com.setPhone("4532".repeat(10));
		userAut.setProfile(com);
		this.makeValidation(userAut,com.getPhone());
		
		com.setPhone("4532+");
		userAut.setProfile(com);
		this.makeValidation(userAut,com.getPhone());
		
		com.setPhone("4532");
		userAut.setProfile(com);
		this.makeValidation(userAut);
		
		
	}
	
	private  void makeValidation(Object toValidate,Object... expectValue) {
		Set<ConstraintViolation<Object>> val=this.validator.validate(toValidate);
		if(expectValue==null) {
	        assertEquals(1,val.size());
	        val.forEach((x)->{
	        	if(null!=x.getInvalidValue()) fail();
	        });
	        return;
		}
		
		if(expectValue.length==0) {assertEquals(true,val.isEmpty());return;}
		AtomicBoolean xx=new AtomicBoolean();	
		xx.set(false);
        assertEquals(expectValue.length,val.size());
        AtomicInteger nullInvalidValue=new AtomicInteger(0);
        AtomicInteger nullExpectValue=new AtomicInteger(0);

		val.forEach((x)->{
			if(x.getInvalidValue()==null) nullInvalidValue.incrementAndGet();
			xx.set(false);
			Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),"ConstraintViolation value: "+x.getInvalidValue());
			for(Object o:expectValue) {
				Log4j2.log.trace(Log4j2.MarkerLog.Test.getMarker(),"expect value: "+o);
				if(o==null) nullExpectValue.incrementAndGet();
				if(x.getInvalidValue()==null) {
					if(o==null) xx.set(true);
				}
				else if(expectValue==null);
				else {
				 if(x.getInvalidValue().equals(o)) xx.set(true);
				}
			}

			assertEquals(true,xx.get());
		});
		assertEquals(nullExpectValue.get()/expectValue.length,nullInvalidValue.get());

	}
}
