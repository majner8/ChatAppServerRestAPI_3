package chatAPP_CommontPart.Properties;



public class WebSocketEndPointPath  {

	public static final String Config_StartConsumingPath="";
	public static final String Config_StopConsumingPath="";
	public static final String AcknowledgeEndPoint_ConfirmMessage="";
	
	public static final String createChatEndPoint="/createChat";
	public static final String chatPreflix="/chat";
	public static final String MessagePreflix=chatPreflix+"/Message";
	public static final String Chat_SendMessagePath=MessagePreflix+"/SendMessage";
	public static final String Chat_sawMessagePath=MessagePreflix+"/sawMessage";
	public static final String Chat_changeMessagePath=MessagePreflix+"/changeMessage";



}
