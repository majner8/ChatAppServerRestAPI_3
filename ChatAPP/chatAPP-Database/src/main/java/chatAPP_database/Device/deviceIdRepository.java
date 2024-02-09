package chatAPP_database.Device;

import java.time.LocalDateTime;
import java.time.ZoneId;


import chatAPP_database.CustomJpaRepository;

public interface deviceIdRepository extends CustomJpaRepository<DeviceIdEntity,String> {

	
	public default void persist(String generatedDeviceID) {
		DeviceIdEntity entity=new DeviceIdEntity();
		entity.setDeviceId(generatedDeviceID);
		entity.setLastSeen(LocalDateTime.now(ZoneId.systemDefault()));
		 this.persist(entity);
	}
}
