package chatAPP_CommontPart.ThreadLocal;

import java.util.Map;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.data.util.Pair;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Security.CustomUserDetailsInterface;
@Component
public class WebSocketThreadLocalSession implements WebSocketThreadLocalSessionInterface, WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue {
	private  final ThreadLocal<Pair<SimpMessageHeaderAccessor,CustomUserDetailsInterface>> wsSession=new ThreadLocal<Pair<SimpMessageHeaderAccessor,CustomUserDetailsInterface>>();

	private String rabitMQContainerHeaderName="xxx";
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.wsSession.remove();
	}

	@Override
	public void setSimpMessageHeaderAccessor(SimpMessageHeaderAccessor session) {
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)session.getUser();
		CustomUserDetailsInterface userDetails=(CustomUserDetailsInterface)authenticationToken.getPrincipal();
		this.wsSession.set(Pair.of(session,userDetails));
	
		
	}
	@Override
	public CustomUserDetailsInterface getCustomUserDetails() {
		// TODO Auto-generated method stub
		return this.wsSession.get().getSecond();
	}

	@Override
	public SimpMessageHeaderAccessor getSimpMessageHeaderAccessor() {
		// TODO Auto-generated method stub
		return this.wsSession.get().getFirst();
	}

	@Override
	public String getConnectionID() {
		// TODO Auto-generated method stub
		return this.getCustomUserDetails().getUsername();
	}

	@Override
	public boolean IsUserConsumingNow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SimpleMessageListenerContainer getSimpleMessageListenerContainer() {
		// TODO Auto-generated method stub
		Map<String,Object> o=this.getSimpMessageHeaderAccessor().getSessionAttributes();
		synchronized(o) {
			 Object x=o.get(this.rabitMQContainerHeaderName);
			 if(x instanceof SimpleMessageListenerContainer) {
				 return (SimpleMessageListenerContainer)x;
			 }
			 return null;
		}
	}

	@Override
	public boolean setSimpleMessageListenerContainer(SimpleMessageListenerContainer container) {
		// TODO Auto-generated method stub
		Map<String,Object> o=this.getSimpMessageHeaderAccessor().getSessionAttributes();
		synchronized(o) {
			Object oo=o.get(this.rabitMQContainerHeaderName);
			if(oo!=null) return false;
			o.put(rabitMQContainerHeaderName, container);
			return true;
		}
	}



}
