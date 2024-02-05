package ChatAPP_RabitMQ.Listener;

public interface RabbitMQConsumerControlInterface {

	public void startConsume(String userdeviceID);
	public void stopConsume(String userdeviceID,boolean DoesDeviceDisconect);
}
