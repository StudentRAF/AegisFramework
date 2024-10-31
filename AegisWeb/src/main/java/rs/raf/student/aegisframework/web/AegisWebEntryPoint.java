package rs.raf.student.aegisframework.web;

import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionManager;
import rs.raf.student.aegisframework.web.dependency_injection.ControllerContainerAnnotation;

@AegisLibrary
public class AegisWebEntryPoint {

    static {
        DependencyInjectionManager.registerContainerAnnotation(new ControllerContainerAnnotation());
    }

}
