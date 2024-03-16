package ChatAPP_Security.Filter.WebSocketFilter;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_database.Chat.UserChatsRepository;

@Component
public class VerifyAccestToChatFilter extends WebSocketFilter{

	@Autowired
	private ChatManagementInterface chatInt;
	@Autowired
	private WebSocketThreadLocalSessionInterface wsSession;
	@Autowired
	private UserChatsRepository chatRepo;
	
	protected VerifyAccestToChatFilter() {
		super(new String[]{WebSocketEndPointPath.chatPreflix}, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runFilter(String callEndPoint) {
		// TODO Auto-generated method stub
	
		this.verifyMessageOwnership(senderBodyId);
		
		Set<Long> memberID=this.chatInt.getUserIDofMembers(chatID, false);
		boolean sucesfull=false;
		
		if(memberID==null) {
			//permision have to be verify from database
			//chat was not loaded for security performance reason
			sucesfull=this.chatRepo
					.existsByPrimaryKeyUserIDAndPrimaryKeyChatID(senderBodyId, chatID);
					
		}
		else {
			sucesfull=memberID.contains(senderBodyId);
		}
		if(!sucesfull) {
			Log4j2.log.info(Log4j2.MarkerLog.Security.getMarker(),
					String.format("Acess denied, user does not have permission to write into chat"
							+"%s chatID : %s %s userID : %s"					
							, System.lineSeparator(),senderBodyId,System.lineSeparator(),senderBodyId));
			throw new AccessDeniedException("AccessDenied");
		}
	}

}
