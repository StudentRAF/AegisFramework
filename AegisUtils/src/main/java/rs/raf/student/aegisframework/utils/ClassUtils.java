package rs.raf.student.aegisframework.utils;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {

    private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    @SneakyThrows
    public static Set<Class<?>> getAllClasses() {
        return ClassPath.from(ClassUtils.class.getClassLoader())
                        .getAllClasses()
                        .stream()
                        .map(ClassUtils::loadClassInfo)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
    }

    public static Class<?> loadClassInfo(ClassPath.ClassInfo classInfo) {
        try {
            return classLoader.loadClass(classInfo.getName());
        }
        catch (Exception | Error ignore) {
            return null;
        } 
    }

    @SneakyThrows
    public static void loadClass(Class<?> classType) {
        Class.forName(classType.getName());
    }

}
