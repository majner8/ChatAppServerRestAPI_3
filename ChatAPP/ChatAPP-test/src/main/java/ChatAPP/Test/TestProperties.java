package ChatAPP.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public  interface TestProperties {
		
		public String getWebSocketEndPointPath(int port);
		
		@Component
		@Profile("chatAPP-Test")
		public class ConfigTest implements TestProperties  {
			  @Value("${websocket.stoamp.endpoint}")
				private String webSocketStoamppreflix;
			  
			@Override
			public String getWebSocketEndPointPath(int port) {
				// TODO Auto-generated method stub
				return "ws://localhost:"+port+"//"+webSocketStoamppreflix;			}

	}
}
