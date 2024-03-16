package ChatAPP_Security.Filter.WebSocketFilter;

import java.util.List;

public abstract class WebSocketFilter  {

	
	/**Constructor create instance, with defined path
	 * If you defined both parametr as null, filter will be apply to all path
	 * @throws IllegalArgumentException if both value are not null */
	protected WebSocketFilter(String[] pathToApplyFilter,String[]pathToSkipFilter) {
		if(pathToApplyFilter!=null&&pathToSkipFilter!=null) {
			throw  new IllegalArgumentException();
		}
	}

	/**Constructor create instance, with defined path
	 * If you defined both parametr as null, filter will be apply to all path
	 * @throws IllegalArgumentException if both value are not null */
	protected WebSocketFilter(List<String> pathToApplyFilter,List<String> pathToSkipFilter) {
		if(pathToApplyFilter!=null&&pathToSkipFilter!=null) {
			throw  new IllegalArgumentException();
		}
	}

	
	
	private static final class WebSocketFilterManager {
		
	}
}
