package rs.raf.student.aegisframework.web.endpoint;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import rs.raf.student.aegisframework.web.http.HttpMethod;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractRequestAnnotation<Type extends Annotation> {

    protected final Class<Type> annotationType;

    public Class<Type> getAnnotationClass() {
        return annotationType;
    }

    public abstract HttpMethod getHttpMethod();

}
