package rs.raf.student.aegisframework.core.annotation;

import rs.raf.student.aegisframework.core.type.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

    Scope scope() default Scope.SINGLETON;

}
