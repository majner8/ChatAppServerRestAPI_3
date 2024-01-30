package ChatAPP_Security.Properties;

public interface SecurityDataProperties {

	public int getMaximumMessagesInOneRequest();
	
	public default boolean doesRequestMeetDataRange(int offSetstart,int offSetEnd) {
		int curretMes=offSetEnd-offSetstart;
		return curretMes<=this.getMaximumMessagesInOneRequest();
	}
}
