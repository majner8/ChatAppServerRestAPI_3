package chatAPP_database.Device;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name=DeviceIdEntity.DeviceIdEntityName)
public class DeviceIdEntity {

	public static final String DeviceIdEntityName="device_id";
	public static final String last_seen_name="last_seen";
	public static final String deviceId_name="deviceid";
	@Id
	@Column(name=deviceId_name)
	 private String deviceId;
	@Column(name=last_seen_name)
	private LocalDateTime lastSeen;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public LocalDateTime getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(LocalDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}
	
	
}
