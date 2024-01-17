package chatAPP_CommontPart.AOP;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessAOPAsync {
	@Async
	public void ProcessAsync(Runnable task) {
		task.run();
	}
}
