package ChatAPP_WebSocket;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import chatAPP_CommontPart.Data.Util.Triple;
import chatAPP_CommontPart.ThreadLocal.RabitMQConsumingMessageProperties;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSimpMessageHeaderAccessor;
import chatAPP_CommontPart.ThreadLocal.ThreadLocalSessionSimpMessageHeaderAccessor;
import chatAPP_CommontPart.ThreadLocal.ThreadLocalSimpMessageHeaderAccessor;

@Service
public class ThreadLocalSessionManagement implements ThreadLocalSessionSimpMessageHeaderAccessor,ThreadLocalSimpMessageHeaderAccessor,RabitMQThreadLocalSimpMessageHeaderAccessor  {

	private final String ContainerListenerHeaderName="AMQListener";
	private ThreadLocal<Triple<SimpMessageHeaderAccessor,RabitMQConsumingMessageProperties,Long>> session=new ThreadLocal<>();
	
	private SimpMessageHeaderAccessor getFirst() {
		return this.session.get().getFirst();
	}
	private RabitMQConsumingMessageProperties  getSecond() {
		return this.session.get().getSecond();
	}
	private long getUserID() {
		return this.session.get().getThird();
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.session.remove();
	}
	
	@Override
	public boolean setSimpleMessageListenerContainer(SimpleMessageListenerContainer container) {
		SimpMessageHeaderAccessor ses=this.session.get().getFirst();
		synchronized(ses) {
			if(ses.getHeader(this.ContainerListenerHeaderName)!=null)return false;
			ses.setHeader(ContainerListenerHeaderName, ses);
		}
		return true;
	}
	@Override
	public long getSessionOwnerUserID() {
		return this.getUserID();
	}

	@Override
	public SimpMessageHeaderAccessor getSimpMessageHeaderAccessor() {
		return this.getFirst();
	}

	@Override
	public boolean IsUserConsumingNow() {
		synchronized(this.getFirst()) {
			if(this.getFirst().getHeader(this.ContainerListenerHeaderName)==null)return false;
			return true;						
		}
	}

	@Override
	public SimpleMessageListenerContainer getSimpleMessageListenerContainer() {
		// TODO Auto-generated method stub
		return (SimpleMessageListenerContainer)this.getFirst().getHeader(this.ContainerListenerHeaderName);
	}

	@Override
	public String getProcessingWebSocketDestination() {
		return this.getSecond().getPath();
	}
	
	@Override
	public RabitMQConsumingMessageProperties getRabitMQMessageProperties() {
		// TODO Auto-generated method stub
		return this.getSecond();
	}
	@Override
	public void setSimpMessageHeaderAccessor(SimpMessageHeaderAccessor par, RabitMQConsumingMessageProperties mesType) {
		this.session.set(Triple.of(par, mesType, Long.valueOf(par.getUser().getName())));
		
	}
	@Override
	public void setSimpMessageHeaderAccessor(SimpMessageHeaderAccessor par) {
		this.session.set(Triple.of(par, null, Long.valueOf(par.getUser().getName())));

	}
	@Override
	public void setSimpMessageHeaderAccessor(RabitMQConsumingMessageProperties mesType) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	



}
