package ChatAPP_Chat.ChatManagement;

import java.util.Set;

public class ActiveChat implements Active_chat_int{


	private final Set<Long> memberID;
	private volatile long LastTimeofUsed;

	public ActiveChat(Set<Long> memberID) {
		this.memberID=memberID;
	}

	public void chatWasUsed() {
		this.LastTimeofUsed=System.currentTimeMillis();
	}

	@Override
	public Set<Long> getUserIDofMembers() {
		// TODO Auto-generated method stub
		return this.memberID;
	}

	@Override
	public boolean isChatActive(long timeout) {
		return (System.currentTimeMillis()-this.LastTimeofUsed)<timeout;
	}


}
