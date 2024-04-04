package ChatAPP_Security.Authorization.DeviceID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import ChatAPP_Security.Authorization.JwtToken.jwtToken;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_database.Device.deviceIdRepository;

@Component
public class deviceIDService {

	@Autowired
	private deviceIdRepository deviceIDRepo;
	@Autowired
	private DeviceIDGenerator deviceIDGenerator;
	@Autowired
	private jwtToken.jwtTokenGenerator jwtTokenGenerator;
	public String generateDeviceID() {


		boolean finish=false;
		DataIntegrityViolationException ex = null;
		int i=0;
		String id=null;
		do {
			if(i!=0) {
				Log4j2.log.warn(Log4j2.MarkerLog.Database.getMarker(),"DataIntegrityViolationException occurs, system try generate Id again");
			}
			id=this.deviceIDGenerator.generateDeviceID();

			try {
			this.deviceIDRepo.persist(id);
			}
			catch(DataIntegrityViolationException e) {
				ex=e;
				i++;
				continue;
			}
			finish=true;
		}
		while(i<3&&!finish);
		if(Log4j2.log.isTraceEnabled()) {
			Log4j2.log.trace(Log4j2.MarkerLog.Security.getMarker(),"Generated random device UUID-UUID: "+id);
		}

		if(!finish) {
			throw ex;
		}

		return id;

	}

	public String generateDeviceJwtToken(String deviceID) {
		return this.jwtTokenGenerator.generateDeviceToken(deviceID);
	}
}
