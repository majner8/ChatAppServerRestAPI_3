package chatAPP_CommontPart.Data.Util;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import chatAPP_CommontPart.Log4j2.Log4j2;
public  abstract class AbstractMultiInstanceBeanValidator<T>{
	public AbstractMultiInstanceBeanValidator(List<T> dependency,Class<?> parentClass ) {
	
		if(dependency.isEmpty()) {
			Log4j2.log.error(Log4j2.MarkerLog.appInit.getMarker(),
					String.format("Error during init multiple bean instance %s inject bean does not contain at least 2 dependency. InterfaceName: %s",
							System.lineSeparator(),parentClass.getTypeName()));
			throw new InsufficientDependenciesException();
		}

		synchronized(dependency) {
		dependency = dependency.stream()
					
                  .filter(manager -> {
                		if(Log4j2.log.isDebugEnabled()) {
                			Log4j2.log.debug(Log4j2.MarkerLog.appInit.getMarker(),
                					String.format("init multiple bean dependency. %s "
                							+ "interface name: %s "
                							+ "%s path to implemented class %s"
                							, System.lineSeparator(),manager.getClass().getTypeName(),
                							System.lineSeparator(),manager.getClass().getPackageName()
                							));
                		}
                		
                  	return true;})
                  
                  .collect(Collectors.toList());
		}
		
			
		
	}
	
	public static class InsufficientDependenciesException extends RuntimeException{
		
	}
	
	
}
