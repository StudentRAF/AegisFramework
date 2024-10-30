package rs.raf.student.aegisframework.core.dependency_injection;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractContainerAnnotation<Type extends Annotation> {

    protected final Class<Type> annotationType;

    public Class<Type> getAnnotationClass() {
        return annotationType;
    }

    public abstract void register(Class<?> caller, Annotation annotation);

}
