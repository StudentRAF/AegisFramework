package rs.raf.student.aegisframework.core.dependency_injection.container;

import rs.raf.student.aegisframework.core.annotation.Component;
import rs.raf.student.aegisframework.core.dependency_injection.AbstractContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionContainer;

import java.lang.annotation.Annotation;

public class ComponentContainerAnnotation extends AbstractContainerAnnotation<Component> {

    public ComponentContainerAnnotation() {
        super(Component.class);
    }

    @Override
    public void register(Class<?> caller, Annotation annotation) {
        DependencyInjectionContainer.registerPrototype(caller);
    }

}
