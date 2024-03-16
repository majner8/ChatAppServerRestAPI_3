package chatAPP_CommontPart.Security;

public interface SecurityParametrsFilter {

	public static interface chatFilter{
		String getChatID();
	}
	public static interface MessageOwnerFilter{
		long getMessageOwner();
	}
}
