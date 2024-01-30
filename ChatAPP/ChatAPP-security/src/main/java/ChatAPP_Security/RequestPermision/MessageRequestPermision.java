package ChatAPP_Security.RequestPermision;

import org.springframework.stereotype.Component;
/**Set security metod, to make verification.
 * All metod also verify if chat exist */
public interface MessageRequestPermision {

	/**Metod verify, if id is same as in session, and eventually make security consequences  */
	public void verifyMessageOwnership(long bodyMessageOwnerID);
	

	public void verifyGetQuickUserOverViewPermision(long SenderID,int offSetStart,int offSetEnd);

	/**Metod verify if sender of message is same as sender from WebSocketSession
	 * ,Also, metod verify if user has permision to write into chat  
	 * */
	public void verifyUserAccestPermisionToChat(long userID,String chatID);
}
