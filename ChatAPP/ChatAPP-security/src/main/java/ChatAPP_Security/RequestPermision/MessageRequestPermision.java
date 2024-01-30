package ChatAPP_Security.RequestPermision;

import org.springframework.stereotype.Component;

public interface MessageRequestPermision {

	/**Metod verify, if id is same, and eventually make security consequences  */
	public void verifyMessageOwnership(long messageOwnerID,long SenderID,long WebSocketSessionOwnerID);
	
	
	/**Metod verify if sender of message is same as sender from WebSocketSession
	 * ,Also, metod verify if user has permision to write into chat  
	 * */
	public  void verifyChatWritePermission(long SenderIDInMessage,long sessionID,String chatID);	
	public void verifyGetQuickUserOverViewPermision(long SenderID,int offSetStart,int offSetEnd);
	
	public void verifyUserAccestPermisionToChat(long userID,String chatID);
	@Component
	public static class justEmpty implements MessageRequestPermision{

		@Override
		public void verifyMessageOwnership(long messageOwnerID, long SenderID, long WebSocketSessionOwnerID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void verifyChatWritePermission(long SenderIDInMessage, long sessionID, String chatID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void verifyGetQuickUserOverViewPermision(long SenderID, int offSetStart, int offSetEnd) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void verifyUserAccestPermisionToChat(long userID, String chatID) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
