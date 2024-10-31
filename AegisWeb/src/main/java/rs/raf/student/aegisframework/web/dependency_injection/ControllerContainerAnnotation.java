package rs.raf.student.aegisframework.web.dependency_injection;

import rs.raf.student.aegisframework.core.dependency_injection.AbstractContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionContainer;
import rs.raf.student.aegisframework.web.annotation.Controller;

import java.lang.annotation.Annotation;

public class ControllerContainerAnnotation extends AbstractContainerAnnotation<Controller> {

    public ControllerContainerAnnotation() {
        super(Controller.class);
    }

    @Override
    public void register(Class<?> caller, Annotation annotation) {
        DependencyInjectionContainer.registerSingleton(caller);
    }

}
