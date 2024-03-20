package chatAPP_CommontPart.ApplicationListener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Data.Util.AbstractMultiInstanceBeanValidator;

public interface WebSocketSessionListener {

	public void UserConnect();
	public void UserDisconnect();
	

	@Component
	@Primary
	public static class WebSocketSessionListenerManager  extends AbstractMultiInstanceBeanValidator<WebSocketSessionListener> implements WebSocketSessionListener{

		private List<WebSocketSessionListener> list;
		
		
		@Autowired
		public WebSocketSessionListenerManager(List<WebSocketSessionListener> list) {
			super(list,WebSocketSessionListenerManager.class); 
			this.list =list;
		}


		@Override
		public void UserConnect() {
			this.list.forEach((x)->{
				x.UserConnect();
			});
		}


		@Override
		public void UserDisconnect() {
			this.list.forEach((x)->{
				x.UserDisconnect();
			});			
		}

		
	}

}
