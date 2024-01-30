package ChatAPP_Chat.ChatManagement;

import java.util.Set;

public interface ChatManagementInterface {
	/**Metod return list of member of chat
	 * @param shouldLoadedChat-true, if active chat does not exist-will be loaded from database
	 * otherwise, it return null*/
	public Set<Long> getUserIDofMembers(String chatID,boolean shouldLoadedChat);

}
