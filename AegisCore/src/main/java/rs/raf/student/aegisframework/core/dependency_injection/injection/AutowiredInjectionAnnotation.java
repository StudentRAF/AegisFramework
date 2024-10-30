package rs.raf.student.aegisframework.core.dependency_injection.injection;

import rs.raf.student.aegisframework.core.annotation.Autowired;
import rs.raf.student.aegisframework.core.dependency_injection.AbstractInjectionAnnotation;

public class AutowiredInjectionAnnotation extends AbstractInjectionAnnotation<Autowired> {

    public AutowiredInjectionAnnotation() {
        super(Autowired.class);
    }

}
