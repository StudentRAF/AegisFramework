package rs.raf.student.aegisframework.core.dependency_injection;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class DependencyInjectionManager {

    private static final Map<Class<? extends Annotation>, AbstractContainerAnnotation<? extends Annotation>> containerAnnotationMap = Maps.newHashMap();
    private static final Set<Class<? extends Annotation>>                                                    injectionAnnotationMap = Sets.newHashSet();

    public static <Type extends Annotation> void registerContainerAnnotation(AbstractContainerAnnotation<Type> containerAnnotation) {
        containerAnnotationMap.put(containerAnnotation.getAnnotationClass(), containerAnnotation);
    }

    public static <Type extends Annotation> void registerInjectionAnnotation(AbstractInjectionAnnotation<Type> injectionAnnotation) {
        injectionAnnotationMap.add(injectionAnnotation.getAnnotationClass());
    }

    public static boolean isContainerAnnotation(Class<?> annotationType) {
        return containerAnnotationMap.containsKey(annotationType);
    }

    public static boolean hasInjectionAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                     .anyMatch(annotation -> injectionAnnotationMap.contains(annotation.annotationType()));
    }

    public static AbstractContainerAnnotation<?> getContainerAnnotation(Class<?> annotationType) {
        return containerAnnotationMap.get(annotationType);
    }

}
