package ChatAPP.Test.Authorization;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

@Component
@Profile("chatAPP-Test")
public class CreateAuthorizationAcessTest {
	private static  AtomicInteger integer=new AtomicInteger();
	static {
		integer.set(0);
	}  
	/** Method returns all headers needed to be processed through AuthorizationFilter */
		public Map<String,String> getAuthorizationHeaders(WebTestClient webTestClient){
			synchronized(this.integer) {
			return null;
			//return this.getFullAuthorizatedUserHeader(this.getUserTokenHeader(this.getDeviceIDHeader(webTestClient),webTestClient),webTestClient);
			}
		}
}
