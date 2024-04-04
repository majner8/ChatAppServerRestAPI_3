package ChatAPP_Chat.ChatConfig;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatPropertiesImplementation implements ChatProperties {

	@Value("${application.chat.MaximumChatTimeout}")
    private Duration chatTimeout;


	@Override
	public long getMaximumAvaiableTimeoutOfActiveChat() {
		// TODO Auto-generated method stub
		return this.chatTimeout.getSeconds();
	}

}
