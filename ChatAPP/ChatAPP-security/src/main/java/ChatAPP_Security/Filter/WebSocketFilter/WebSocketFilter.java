package ChatAPP_Security.Filter.WebSocketFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Security.applyWebSocketFilter;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

public abstract class WebSocketFilter<T>  implements applyWebSocketFilter {

	@Autowired
	protected WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue wsSession;
	
	private final Class<T> objectToVerify;
	private List<String> pathToSkip=new ArrayList<String>();
	private List<String>pathToApply=new ArrayList<String>();
	private boolean applyEveryTime=false;;

	
	/**Constructor create instance, with defined path
	 * If you defined both parametr as null, filter will be apply to all path, instead of defined path in skip
	*/
	protected WebSocketFilter(Class<T>objectToVerify,String[] RegexpathToApplyFilter,String[]RegexpathToSkipFilter) {
		this.objectToVerify=objectToVerify;
		if(RegexpathToApplyFilter==null) {
			this.applyEveryTime=true;
		}
		if(RegexpathToSkipFilter==null) {
			return;
		}
		this.pathToApply=Arrays.asList(RegexpathToApplyFilter);
		this.pathToSkip=Arrays.asList(RegexpathToSkipFilter);
		
	}

	/**Constructor create instance, with defined path
	 * If you defined both parametr as null, filter will be apply to all path, instead of defined path in skip
	*/
	protected WebSocketFilter(Class<T>objectToVerify,List<String> RegexpathToApplyFilter,List<String> RegexpathToSkipFilter) {
		this.objectToVerify=objectToVerify;
		if(RegexpathToApplyFilter==null) {
			this.applyEveryTime=true;
		}
		if(RegexpathToSkipFilter==null) {
			return;
		}
		this.pathToApply=RegexpathToApplyFilter;
		this.pathToSkip=RegexpathToSkipFilter;
	}
	
	@Override
	public final void applyFilter(String callEndPoint,Object [] param) {
		if(Log4j2.log.isDebugEnabled()) {
			Log4j2.log.debug(Log4j2.MarkerLog.Security.getMarker(),
					"Apply Ws security filter"+this.getClass().getName());
		}
		
		if(this.pathToSkip.stream().anyMatch(s->s.matches(callEndPoint))) return;
		if(this.applyEveryTime) {
			this.doFilter(callEndPoint, param);
			return;
		}	
		
		if(this.pathToApply.stream().anyMatch(s->s.matches(callEndPoint))) this.doFilter(callEndPoint, param);;
		return;
	}
	private void doFilter(String callEndPoint,Object [] param) {
		
		T ob=null;
		for(Object object:param) {
			if(this.objectToVerify.isInstance(object)) {
				ob=(T)object;
				break;
			}
		}
		if(ob==null)throw  new IllegalArgumentException();

		this.runFilter(callEndPoint, ob);
	}
	public abstract void runFilter(String callEndPoint,T parametrObject);
	
	@Component
	@Primary
	private static final class WebSocketFilterManager implements applyWebSocketFilter {
		
		private List<WebSocketFilter> filters;
		@Autowired
		public WebSocketFilterManager(List<WebSocketFilter> filter) {
			this.filters=filter;
			
		}
		
		@Override
		public void applyFilter(String callEndPoint,Object [] para) {
			Iterator<WebSocketFilter> fil=this.filters.iterator();
			
			try {
				while (fil.hasNext()) {

					fil.next().applyFilter(callEndPoint, para);
				} 
			} catch (AccessDeniedException e) {
				if(Log4j2.log.isDebugEnabled()) {
					Log4j2.log.debug(Log4j2.MarkerLog.Security.getMarker(),
							String.format("Security webSocket filter denied access, filter name %s", 
									this.getClass().getName()));
					
				}
				throw e;
			}
		}
		
	}
}
