package chatAPP_CommontPart.Data.Util;

import java.util.List;
import java.util.stream.Collectors;


public  abstract class AbstractMultiInstanceBeanValidator{

	public <T> AbstractMultiInstanceBeanValidator(List<T> dependency,Class<?> parentClass ) {
		if(dependency.size()<=1) {
			throw new InsufficientDependenciesException();
		}
		synchronized(dependency) {
		dependency = dependency.stream()
                  .filter(manager -> !parentClass.isInstance(manager))
                  .collect(Collectors.toList());
		}
	}
	
	public static class InsufficientDependenciesException extends RuntimeException{
		
	}
}
