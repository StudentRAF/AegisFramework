package rs.raf.student.aegisframework.core.scanner;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class AnnotationScanner {

    private static final Field[]  EMPTY_FIELDS  = {};
    private static final Method[] EMPTY_METHODS = {};

    private static final Set<Class<?>>                                   libraryClassSet      = Sets.newHashSet();
    private static final Multimap<Class<?>, Class<? extends Annotation>> classAnnotationsMap  = ArrayListMultimap.create();
    private static final Multimap<Class<? extends Annotation>, Class<?>> annotationClassesMap = ArrayListMultimap.create();
    private static final Multimap<Class<? extends Annotation>, Field>    annotationFieldsMap  = ArrayListMultimap.create();
    private static final Multimap<Class<? extends Annotation>, Method>   annotationMethodsMap = ArrayListMultimap.create();

    @SneakyThrows
    public static void scan() {
        scanLibraries();
        scanTypes();
    }

    private static void scanLibraries() {
        ClassScanner.getClasses()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(currentClass -> Arrays.stream(currentClass.getAnnotations())
                                                   .map(Annotation::annotationType)
                                                   .filter(type -> type.isAssignableFrom(AegisLibrary.class))
                                                   .forEach(type -> registerLibrary(currentClass)));

        System.out.print(new StringBuilder().appendSeparatorWide());
    }


    private static void registerLibrary(Class<?> registerClass) {
        libraryClassSet.add(registerClass);

        System.out.print(new StringBuilder().appendFormattedLine("{0}: {1}",
                                                                 "Register Library".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                   .applyAttribute(Attribute.UNDERLINE),
                                                                 registerClass.getName()
                                                                              .applyColorAttribute(Attribute.SET_FOREGROUND, Color.FUCHSIA)));
    }

    private static void scanTypes() {
        ClassScanner.getApplicationClasses()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(AnnotationScanner::registerClass);
    }

    private static void registerClass(Class<?> typeClass) {
        Arrays.stream(typeClass.getDeclaredAnnotations())
              .forEach(annotation -> {
                  classAnnotationsMap.put(typeClass, annotation.annotationType());
                  annotationClassesMap.put(annotation.annotationType(), typeClass);
              });

        Arrays.stream(getFields(typeClass))
              .forEach(AnnotationScanner::registerField);

        Arrays.stream(getMethods(typeClass))
              .forEach(AnnotationScanner::registerMethod);
    }

    private static Field[] getFields(Class<?> typeClass) {
        try {
            return typeClass.getDeclaredFields();
        }
        catch (Error ignore) {
            return EMPTY_FIELDS;
        }
    }

    private static Method[] getMethods(Class<?> typeClass) {
        try {
            return typeClass.getDeclaredMethods();
        }
        catch (Error ignore) {
            return EMPTY_METHODS;
        }
    }

    private static void registerField(Field field) {
        Arrays.stream(field.getAnnotations())
              .forEach(annotation -> annotationFieldsMap.put(annotation.annotationType(), field));
    }

    private static void registerMethod(Method method) {
        Arrays.stream(method.getAnnotations())
              .forEach(annotation -> annotationMethodsMap.put(annotation.annotationType(), method));
    }

    public static List<Field> getAnnotationFields(Class<? extends Annotation> annotation) {
        return annotationFieldsMap.get(annotation)
                                  .stream()
                                  .toList();
    }

    public static List<Class<?>> getLibraryClasses() {
        return libraryClassSet.stream()
                              .toList();
    }

}
