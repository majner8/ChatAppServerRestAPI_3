package ChatAPP_Chat.ChatManagement;

import java.util.List;
import java.util.Set;

public interface Active_chat_int {

	public Set<Long> getUserIDofMembers();
	
	public boolean isChatActive(long maximumTimeout);
	
	

}
