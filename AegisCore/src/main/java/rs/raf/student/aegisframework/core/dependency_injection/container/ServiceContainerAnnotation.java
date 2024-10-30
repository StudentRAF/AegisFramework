package rs.raf.student.aegisframework.core.dependency_injection.container;

import rs.raf.student.aegisframework.core.annotation.Service;
import rs.raf.student.aegisframework.core.dependency_injection.AbstractContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionContainer;

import java.lang.annotation.Annotation;

public class ServiceContainerAnnotation extends AbstractContainerAnnotation<Service> {

    public ServiceContainerAnnotation() {
        super(Service.class);
    }

    @Override
    public void register(Class<?> caller, Annotation annotation) {
        DependencyInjectionContainer.registerSingleton(caller);
    }

}
