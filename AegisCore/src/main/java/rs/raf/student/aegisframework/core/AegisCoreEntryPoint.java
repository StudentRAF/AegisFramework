package rs.raf.student.aegisframework.core;

import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.core.dependency_injection.DependencyInjectionManager;
import rs.raf.student.aegisframework.core.dependency_injection.container.BeanContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.container.ComponentContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.container.QualifierContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.container.ServiceContainerAnnotation;
import rs.raf.student.aegisframework.core.dependency_injection.injection.AutowiredInjectionAnnotation;

@AegisLibrary
public class AegisCoreEntryPoint {

    static {
        DependencyInjectionManager.registerContainerAnnotation(new BeanContainerAnnotation());
        DependencyInjectionManager.registerContainerAnnotation(new ComponentContainerAnnotation());
        DependencyInjectionManager.registerContainerAnnotation(new QualifierContainerAnnotation());
        DependencyInjectionManager.registerContainerAnnotation(new ServiceContainerAnnotation());

        DependencyInjectionManager.registerInjectionAnnotation(new AutowiredInjectionAnnotation());
    }

}
