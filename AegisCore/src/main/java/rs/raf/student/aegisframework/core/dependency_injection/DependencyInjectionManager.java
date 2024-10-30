package rs.raf.student.aegisframework.core.dependency_injection;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class DependencyInjectionManager {

    private static final Map<Class<? extends Annotation>, AbstractContainerAnnotation<? extends Annotation>> containerAnnotationMap = Maps.newHashMap();
    private static final Set<Class<? extends Annotation>>                                                    injectionAnnotationMap = Sets.newHashSet();

    public static <Type extends Annotation> void registerContainerAnnotation(AbstractContainerAnnotation<Type> containerAnnotation) {
        containerAnnotationMap.put(containerAnnotation.getAnnotationClass(), containerAnnotation);

        System.out.print(new StringBuilder().appendFormattedLine("{0}: {1}",
                                                                 "Register Container Annotation".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                                .applyAttribute(Attribute.UNDERLINE),
                                                                 containerAnnotation.getAnnotationClass().getName()
                                                                                    .applyColorAttribute(Attribute.SET_FOREGROUND, Color.BLUE)));
    }

    public static <Type extends Annotation> void registerInjectionAnnotation(AbstractInjectionAnnotation<Type> injectionAnnotation) {
        injectionAnnotationMap.add(injectionAnnotation.getAnnotationClass());

        System.out.print(new StringBuilder().appendFormattedLine("{0}:    {1}",
                                                                 "Register Inject Annotation".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                             .applyAttribute(Attribute.UNDERLINE),
                                                                 injectionAnnotation.getAnnotationClass().getName()
                                                                                    .applyColorAttribute(Attribute.SET_FOREGROUND, Color.YELLOW)));
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
