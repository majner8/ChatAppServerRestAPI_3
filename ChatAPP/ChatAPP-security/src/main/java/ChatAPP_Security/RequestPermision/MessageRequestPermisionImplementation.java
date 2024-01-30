package ChatAPP_Security.RequestPermision;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import ChatAPP_Security.Properties.SecurityDataProperties;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Security.CustomException.InvalidRequestRangeException;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_database.Chat.UserChatsRepository;

@Component
public class MessageRequestPermisionImplementation implements MessageRequestPermision{

	@Autowired
	private SecurityDataProperties dataProp;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue webSocketSession;
	@Autowired
	private ChatManagementInterface chatInt;
	@Autowired
	private UserChatsRepository chatRepo;
	

	

	@Override
	public void verifyGetQuickUserOverViewPermision(long SenderID, int offSetStart, int offSetEnd) {
		this.verifyMessageOwnership(SenderID);

		if(!this.dataProp.doesRequestMeetDataRange(offSetStart, offSetEnd)) {
			Log4j2.log.info(Log4j2.MarkerLog.Security.getMarker(),
					String.format(""
							+ "Access was denied, offSet is out of range"
							+ "%s offSetStart %s %s offSetEnd %s",System.lineSeparator(),offSetStart,System.lineSeparator(),offSetEnd));
			throw new InvalidRequestRangeException();
		}
	}

	@Override
	public void verifyUserAccestPermisionToChat(long senderBodyId, String chatID) {
		this.verifyMessageOwnership(senderBodyId);
		
		Set<Long> memberID=this.chatInt.getUserIDofMembers(chatID, false);
		boolean sucesfull=false;
		
		if(memberID==null) {
			//permision have to be verify from database
			//chat was not loaded for security performance reason
			sucesfull=this.chatRepo.existsByPrimaryKeyUserIdAndPrimaryKeyChatId(senderBodyId, chatID);
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

	@Override
	public void verifyMessageOwnership(long bodyMessageOwnerID) {
		if(this.webSocketSession.getSessionOwnerUserID()!=bodyMessageOwnerID) {
			Log4j2.log.info(Log4j2.MarkerLog.Security.getMarker(),
					String.format("VerifyMessageOwnership was not sucesfull, ownerID is not same"
							+"%s MessageIDOwner: %s %s sessionID: %s"					
							, System.lineSeparator(),bodyMessageOwnerID,System.lineSeparator(),this.webSocketSession.getSessionOwnerUserID()));
			throw new AccessDeniedException("AccessDenied");
		}
		
		
	}

}
