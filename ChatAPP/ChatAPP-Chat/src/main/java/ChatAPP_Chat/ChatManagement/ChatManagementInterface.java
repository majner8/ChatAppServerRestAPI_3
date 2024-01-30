package ChatAPP_Chat.ChatManagement;

import java.util.List;

import org.springframework.stereotype.Component;

/*Interface work with threadLocal* */
public interface ChatManagementInterface {

	public List<Long> getUserIDofMembers();
	@Component
	public static class justForComponent implements ChatManagementInterface{

		@Override
		public List<Long> getUserIDofMembers() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
