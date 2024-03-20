package chatAPP_CommontPart.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public interface BeanInitAnnotation {

    /** 
     * Annotation to indicate that an injectable bean should be applied first in the initialization order. 
     */
    @Retention(RetentionPolicy.RUNTIME) // Make it available at runtime for reflection
    @Target(ElementType.TYPE) // Applicable to classes, interfaces, or enums
    public static @interface First{}

    /** 
     * Annotation to indicate that an injectable bean should be applied last in the initialization order.
     */
    @Retention(RetentionPolicy.RUNTIME) // Make it available at runtime for reflection
    @Target(ElementType.TYPE) // Applicable to classes, interfaces, or enums
    public static @interface Last{}
}
