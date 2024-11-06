package rs.raf.student.aegisframework.web.scanner;

import com.google.common.collect.Sets;
import rs.raf.student.aegisframework.core.scanner.AnnotationScanner;
import rs.raf.student.aegisframework.web.annotation.Controller;

import java.util.Set;

public class WebScanner {

    private static final Set<Class<?>> controllerClasses = Sets.newHashSet();

    public static void scan() {
        controllerClasses.addAll(AnnotationScanner.getAnnotationClasses(Controller.class));
    }

    public static Set<Class<?>> controllerClasses() {
        return controllerClasses;
    }

}
