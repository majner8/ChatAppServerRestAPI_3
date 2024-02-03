package ChatAPP_Security.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityDataImplementation implements SecurityDataProperties {

	@Value("${security.request.MaximumMessageInOneRequest}")
    private int MaximumMessageInOneRequest;

	@Override
	public int getMaximumMessagesInOneRequest() {
		// TODO Auto-generated method stub
		return this.MaximumMessageInOneRequest;
	}

}
