package ChatAPP_Security.Filter.WebSocketFilter;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_CommontPart.Security.SecurityParametrsFilter.chatFilter;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_database.Chat.UserChatsRepository;

@Component
public class VerifyAccestToChatFilter extends WebSocketFilter<chatFilter>{

	@Autowired
	private ChatManagementInterface chatInt;
	@Autowired
	private UserChatsRepository chatRepo;
	
	protected VerifyAccestToChatFilter() {
		super(chatFilter.class,new String[]{WebSocketEndPointPath.chatPreflix+".*"}, null);
		// TODO Auto-generated constructor stub
	}

	


	@Override
	public void runFilter(String callEndPoint, chatFilter parametrObject) {
		Set<Long> memberID=this.chatInt.getUserIDofMembers(parametrObject.getChatID(), false);
		boolean sucesfull=false;
		long userID=this.wsSession.getSessionOwnerUserID();
		if(memberID==null) {
			sucesfull=this.chatRepo
			.existsByPrimaryKeyUserIDAndPrimaryKeyChatID(userID, parametrObject.getChatID());	
		}
		else {
			sucesfull=memberID.contains(userID);
		}
		
		if(!sucesfull) {
			Log4j2.log.info(Log4j2.MarkerLog.Security.getMarker(),
					String.format("Acess denied, user does not have permission to write into chat"
							+"%s chatID : %s %s userID : %s"					
							, System.lineSeparator(),parametrObject.getChatID(),System.lineSeparator(),userID));
			throw new AccessDeniedException("AccessDenied");
		}		
	}


	

}
