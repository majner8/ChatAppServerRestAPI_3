package ChatAPP_RabitMQ.Queue;

import ChatAPP_RabitMQ.Queue.RabbitMQQueueManager.RabitMQQueue;

public interface RabbitMQQueueManagerInterface {

	/**Metod return deviceQueueName
	 * if queue has not been created yet, metod manage creating new one
	 *, bind them to user Key

	 *  */
	public RabitMQQueue getDeviceQueueName();
}

