package rs.raf.student.aegisframework.core.dependency_injection.container;

import rs.raf.student.aegisframework.core.annotation.Qualifier;
import rs.raf.student.aegisframework.core.dependency_injection.AbstractContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionContainer;

import java.lang.annotation.Annotation;

public class QualifierContainerAnnotation extends AbstractContainerAnnotation<Qualifier> {

    public QualifierContainerAnnotation() {
        super(Qualifier.class);
    }

    @Override
    public void register(Class<?> caller, Annotation annotation) {
        DependencyInjectionContainer.registerClassQualifier(caller, ((Qualifier)annotation).value());
    }

}
