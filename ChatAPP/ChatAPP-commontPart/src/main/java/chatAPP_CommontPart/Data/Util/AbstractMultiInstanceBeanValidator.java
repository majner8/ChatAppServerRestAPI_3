package chatAPP_CommontPart.Data.Util;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import chatAPP_CommontPart.AOP.BeanInitAnnotation;
import chatAPP_CommontPart.Log4j2.Log4j2;
public  abstract class AbstractMultiInstanceBeanValidator<T>{
	public AbstractMultiInstanceBeanValidator(List<T> dependency,Class<?> parentClass ) {
	
		if(dependency.isEmpty()) {
			Log4j2.log.error(Log4j2.MarkerLog.appInit.getMarker(),
					String.format("Error during init multiple bean instance %s inject bean does not contain at least 2 dependency. InterfaceName: %s",
							System.lineSeparator(),parentClass.getTypeName()));
			throw new InsufficientDependenciesException();
		}
		 ArrayList<T>first=new ArrayList<>();
		 ArrayList<T>last=new ArrayList<>();

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
                		//filter bean, which is primary
                		if(parentClass.isInstance(manager)) {
                			return false;
                		}
                		if(manager.getClass().isAnnotationPresent(BeanInitAnnotation.First.class)) {
                			//should be put as first
                			first.add(manager);
                			return false;
                		}
                		if(manager.getClass().isAnnotationPresent(BeanInitAnnotation.Last.class)) {
                			//should be put as last
                			last.add(manager);
                			return false;
                		}
                  	return true;})
                  
                  .collect(Collectors.toList());
		}
		if(!first.isEmpty()) {
			if(first.size()>1) {
				Log4j2.log.warn(Log4j2.MarkerLog.Bean.getMarker(),
						String.format("Found more then one Bean, annotation by %s annotation, primaryClass: %s", BeanInitAnnotation.First.class.getName(),parentClass.getClass().getName()));
			}
			for(T x:first) {
				if(first.size()>1) {
				Log4j2.log.debug(Log4j2.MarkerLog.Bean.getMarker(),
						String.format("Found more then one Bean, annotation by %s annotation, name of bean", BeanInitAnnotation.First.class.getName(),x.getClass().getName()));
				}
				dependency.add(0, x);
			}
		}
		
		if(!last.isEmpty()) {
			if(last.size()>1) {
				Log4j2.log.warn(Log4j2.MarkerLog.Bean.getMarker(),
						String.format("Found more then one Bean, annotation by %s annotation, primaryClass: %s", BeanInitAnnotation.Last.class.getName(),parentClass.getClass().getName()));
			}
			
			int lastPosition=dependency.size();
			for(T x:last) {
				if(first.size()>1) {
					Log4j2.log.debug(Log4j2.MarkerLog.Bean.getMarker(),
							String.format("Found more then one Bean, annotation by %s annotation, name of bean", BeanInitAnnotation.Last.class.getName(),x.getClass().getName()));
					}
				dependency.add(lastPosition, x);
				lastPosition=lastPosition+1;
			}
		}	
		
	}
	
	public static class InsufficientDependenciesException extends RuntimeException{
		
	}
	
	
}
