package ChatAPP_Security.Filter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import chatAPP_CommontPart.Data.Util.AbstractMultiInstanceBeanValidator;



public interface DefineFilterSkipPath {

	
	public static interface pathForDeviceIdFilter{
		public List<String> getPathForDeviceIDFilter();
		@Primary
		@Component
		public static class MainDefinePathToSkip extends AbstractMultiInstanceBeanValidator implements pathForDeviceIdFilter{
			
			private	List<String>pathToSkip;
			@Autowired
			public MainDefinePathToSkip(List<pathForDeviceIdFilter> list) {
				super(list,MainDefinePathToSkip.class);
				  this.pathToSkip=list.stream()
						  .flatMap((element)->{
							return element.getPathForDeviceIDFilter().stream();  
						  }).toList();
						  }
			@Override
			public List<String> getPathForDeviceIDFilter() {
				// TODO Auto-generated method stub
				return this.pathToSkip;
			}
			}
	}

	public static interface pathForAuthorizationFilterFilter{
		public List<String> getPathAuthorizationFilterFilter();
		@Primary
		@Component
		public static class MainDefinePathToSkip extends AbstractMultiInstanceBeanValidator implements pathForAuthorizationFilterFilter{
			
			private	List<String>pathToSkip;
			@Autowired
			public MainDefinePathToSkip(List<pathForAuthorizationFilterFilter> list) {
				super(list,MainDefinePathToSkip.class);
				  this.pathToSkip=list.stream()
						  .flatMap((element)->{
							return element.getPathAuthorizationFilterFilter().stream();  
						  }).toList();
						  }
			@Override
			public List<String> getPathAuthorizationFilterFilter() {
				// TODO Auto-generated method stub
				return this.pathToSkip;
			}
			}
	}
}
