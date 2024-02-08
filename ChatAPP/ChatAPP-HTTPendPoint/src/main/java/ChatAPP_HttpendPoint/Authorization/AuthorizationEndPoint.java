package ChatAPP_HttpendPoint.Authorization;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import ChatAPP_Security.Authorization.UnAuthorizatePath;
import ChatAPP_Security.Filter.DefineFilterSkipPath;
import chatAPP_DTO.Authorization.TokenDTO;
import chatAPP_DTO.User.UserDTO.UserAuthorizationDTO;
import chatAPP_DTO.User.UserDTO.UserProfileRegistrationDTO;

public interface AuthorizationEndPoint {


	@PostMapping(value="${httpEndPointPath.Authorization.register}")
	public ResponseEntity<TokenDTO> register(@RequestAttribute String deviceID,@RequestBody @Valid UserAuthorizationDTO 
			userData);
	@PostMapping(value="${httpEndPointPath.Authorization.login}")
	public ResponseEntity<TokenDTO> login(@RequestAttribute String deviceID,@RequestBody @Valid UserAuthorizationDTO 
			userData);
	@PostMapping(value="${httpEndPointPath.Authorization.finishRegistration}")
	public ResponseEntity<TokenDTO>finishRegistration(@RequestAttribute String deviceID,@RequestBody @Valid UserProfileRegistrationDTO user);
	
	@Component
	public static final class AuthorizationPath implements UnAuthorizatePath,DefineFilterSkipPath.pathForAuthorizationFilterFilter{


		private List<String> path;
		private String[] UnPath;
		
		public AuthorizationPath(
				@Value("${httpEndPointPath.Authorization.register}")
				String register, 
				@Value("${httpEndPointPath.Authorization.login}")
				String login) {
			this.UnPath=new String[] {login,register};
			this.path=List.of(register,login);
		}

		@Override
		public String[] getUnAuthorizatedPath() {
			// TODO Auto-generated method stub
			return this.UnPath;
		}

		

		@Override
		public List<String> getPathAuthorizationFilterFilter() {
			// TODO Auto-generated method stub
			return this.path;
		}
		
	}

	

}
