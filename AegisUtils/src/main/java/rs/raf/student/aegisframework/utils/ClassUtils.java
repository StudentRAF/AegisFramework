package rs.raf.student.aegisframework.utils;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {

    @SneakyThrows
    public static Set<Class<?>> getAllClasses() {
        return ClassPath.from(ClassUtils.class.getClassLoader())
                        .getAllClasses()
                        .stream()
                        .map(ClassUtils::loadClassInfo)
                        .collect(Collectors.toSet());
    }

    public static Class<?> loadClassInfo(ClassPath.ClassInfo classInfo) {
        try {
            return classInfo.load();
        }
        catch (LinkageError error) {
            return null;
        }
    }

    @SneakyThrows
    public static void loadClass(Class<?> classType) {
        Class.forName(classType.getName());
    }

}
