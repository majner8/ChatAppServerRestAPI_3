package ChatAPP_WebSocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${websocket.stoamp.endpoint}")
	private String webSocketStoamppreflix;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        Log4j2.log.info(Log4j2.MarkerLog.StartApp.getMarker(),"I am setting WebSocketStoamp EndPoint, Path: "+this.webSocketStoamppreflix);
    	registry.addEndpoint(this.webSocketStoamppreflix)

    	.withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
    }

}