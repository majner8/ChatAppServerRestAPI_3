package ChatAPP_Security.Authorization.DeviceID;

import org.springframework.stereotype.Component;

public interface DeviceIDGenerator {
	/**Metod generate deviceId and persist them
	 * Metod has implement mechanism to prevent duplicate id error */
	public String generateDeviceID();
	
	
	@Component
	public static class DeviceIDGeneratorImplement implements DeviceIDGenerator {

		@Override
		public String generateDeviceID() {
			// TODO Auto-generated method stub
			return null;
		}}
}
