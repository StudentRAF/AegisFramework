package rs.raf.student.aegisframework.core.scanner;

import com.google.common.collect.Sets;
import rs.raf.student.aegisframework.core.AegisApplication;
import rs.raf.student.aegisframework.utils.ClassUtils;

import java.util.Objects;
import java.util.Set;

public class ClassScanner {

    private static final Set<Class<?>> loaderClasses      = ClassUtils.getAllClasses();
    private static final Set<Class<?>> applicationClasses = Sets.newHashSet();

    public static void scan() {
        applicationClasses.addAll(loaderClasses.stream()
                                               .filter(Objects::nonNull)
                                               .filter(classInfo -> classInfo.getPackageName()
                                                                             .startsWith(AegisApplication.getBasePackage()))
                                               .toList());
    }

    public static Set<Class<?>> getClasses() {
        return loaderClasses;
    }

    public static boolean isApplicationClass(Class<?> typeClass) {
        return applicationClasses.contains(typeClass);
    }

    public static Set<Class<?>> getApplicationClasses() {
        return applicationClasses;
    }

}
