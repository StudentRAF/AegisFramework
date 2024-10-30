package rs.raf.student.aegisframework.core.dependency_injection.container;

import rs.raf.student.aegisframework.core.annotation.Bean;
import rs.raf.student.aegisframework.core.dependency_injection.AbstractContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionContainer;
import rs.raf.student.aegisframework.core.type.Scope;

import java.lang.annotation.Annotation;

public class BeanContainerAnnotation extends AbstractContainerAnnotation<Bean> {

    public BeanContainerAnnotation() {
        super(Bean.class);
    }

    @Override
    public void register(Class<?> caller, Annotation annotation) {
        if (((Bean) annotation).scope().equals(Scope.SINGLETON))
            DependencyInjectionContainer.registerSingleton(caller);
        else
            DependencyInjectionContainer.registerPrototype(caller);
    }

}
