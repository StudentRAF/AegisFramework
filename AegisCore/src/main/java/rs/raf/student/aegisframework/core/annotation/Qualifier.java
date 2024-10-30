package rs.raf.student.aegisframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
    ElementType.FIELD,
    ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

    Class<?> value();

}
