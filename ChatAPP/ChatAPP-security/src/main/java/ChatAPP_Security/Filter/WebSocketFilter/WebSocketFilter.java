package ChatAPP_Security.Filter.WebSocketFilter;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Security.applyWebSocketFilter;

public abstract class WebSocketFilter  implements applyWebSocketFilter {

	private List<String> pathToSkip;
	private List<String>pathToApply;
	private boolean shouldBeApplyEveryTime;
	private boolean definedPathToSkipPath;
	
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
	
	@Override
	public final void applyFilter(String callEndPoint) {
		if(this.shouldBeApplyEveryTime) {
			this.runFilter(callEndPoint);
			return;
		}
		if(this.definedPathToSkipPath) {
			if(this.pathToSkip.contains(callEndPoint)) {
				return;
			}
			this.runFilter(callEndPoint);
			return;
		}
		else {
			if(this.pathToApply.contains(callEndPoint)) {
				this.runFilter(callEndPoint);
				return;
			}
			return;
		}
		
	}

	public abstract void runFilter(String callEndPoint);
	
	@Component
	@Primary
	private static final class WebSocketFilterManager implements applyWebSocketFilter {
		
		private List<WebSocketFilter> filters;
		@Autowired
		public WebSocketFilterManager(List<WebSocketFilter> filter) {
			this.filters=filter;
			
		}
		
		@Override
		public void applyFilter(String callEndPoint) {
			Iterator<WebSocketFilter> fil=this.filters.iterator();
			while(fil.hasNext()) {
				fil.next().applyFilter(callEndPoint);
			}
		}
		
	}
}
