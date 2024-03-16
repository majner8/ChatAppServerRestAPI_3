package ChatAPP_HttpendPoint.Authorization;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import ChatAPP_Security.Authorization.UnAuthorizatePath;
import ChatAPP_Security.Filter.HttpRequestFilter.DefineFilterSkipPath;


public interface DeviceIDEndPoint {


	/**Metod reuturn device ID token, have to be send with every request */
	@GetMapping(value="${httpEndPointPath.Authorization.deviceID}")
	public ResponseEntity<String> getDeviceIDToken(HttpServletRequest request);
	
	@Component
	public static final class AuthorizationPath implements UnAuthorizatePath,DefineFilterSkipPath.pathForDeviceIdFilter,
	DefineFilterSkipPath.pathForAuthorizationFilterFilter{

		private String[] deviceID;
		private List<String>path;
		

		public AuthorizationPath(
				@Value("${httpEndPointPath.Authorization.deviceID}")
				String deviceID) {
			this.deviceID = new String[]{deviceID};
			this.path=List.of(deviceID);
		}



		@Override
		public String[] getUnAuthorizatedPath() {
			// TODO Auto-generated method stub
			return this.deviceID;
		}



		@Override
		public List<String> getPathForDeviceIDFilter() {
			// TODO Auto-generated method stub
			return this.path;
		}



		@Override
		public List<String> getPathAuthorizationFilterFilter() {
			// TODO Auto-generated method stub
			return this.path;
		}
		
	}
}
