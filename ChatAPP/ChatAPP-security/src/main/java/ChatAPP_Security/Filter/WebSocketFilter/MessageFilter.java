package ChatAPP_Security.Filter.WebSocketFilter;

import org.springframework.security.access.AccessDeniedException;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_CommontPart.Security.SecurityParametrsFilter.MessageOwnerFilter;

public class MessageFilter extends WebSocketFilter<MessageOwnerFilter> {

	protected MessageFilter() {
		super(MessageOwnerFilter.class, 
				new String[] {WebSocketEndPointPath.MessagePreflix+".*"},
				null);
		
	}

	@Override
	public void runFilter(String callEndPoint, MessageOwnerFilter parametrObject) {
		if(super.wsSession.getSessionOwnerUserID()!=parametrObject.getMessageOwner()) {
			Log4j2.log.warn(Log4j2.MarkerLog.Security.getMarker(),
					String.format("VerifyMessageOwnership was not sucesfull, ownerID is not same"
							+"%s MessageIDOwner: %s %s sessionID: %s"					
							, System.lineSeparator(),parametrObject.getMessageOwner(),System.lineSeparator(),super.wsSession.getSessionOwnerUserID()));
			throw new AccessDeniedException("AccessDenied");
		}
	}

}
