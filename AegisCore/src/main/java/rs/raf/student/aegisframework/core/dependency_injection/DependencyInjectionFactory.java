package rs.raf.student.aegisframework.core.dependency_injection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.core.scanner.ClassScanner;
import rs.raf.student.aegisframework.utils.Pair;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class DependencyInjectionFactory {

    private static final Multimap<Class<?>, Class<?>> dependencyMap = ArrayListMultimap.create();

    public static void initialise() {
        initialiseContainer();
        injectDependencies();
    }

    private static void initialiseContainer() {
        System.out.print(new StringBuilder().appendSeparatorWide()
                                            .appendLine("     Initialise Container")
                                            .appendSeparatorWide());

        ClassScanner.getApplicationClasses()
                    .stream()
                    .<Pair<Class<?>, Annotation>>mapMulti((classType, consumer) -> Arrays.stream(classType.getAnnotations())
                                                                                         .forEach(annotation -> consumer.accept(Pair.of(classType, annotation))))
                    .filter(pair -> DependencyInjectionManager.isContainerAnnotation(pair.getValue().annotationType()))
                    .forEach(pair -> DependencyInjectionManager.getContainerAnnotation(pair.getValue().annotationType())
                                                               .register(pair.getKey(), pair.getValue()));

        System.out.print(new StringBuilder().appendSeparatorWide());
    }

    private static void injectDependencies() {
        System.out.print(new StringBuilder().appendLine("     Inject Dependencies")
                                            .appendSeparatorWide());

        ClassScanner.getApplicationClasses()
                    .forEach(classType -> Arrays.stream(classType.getDeclaredFields())
                                                .filter(field -> DependencyInjectionManager.hasInjectionAnnotation(field.getDeclaredAnnotations()))
                                                .forEach(field -> injectFieldDependency(classType, field)));

        System.out.print(new StringBuilder().appendSeparatorWide());
    }

    @SneakyThrows
    private static void injectFieldDependency(Class<?> classType, Field field) {
        Object classInstance = DependencyInjectionContainer.getSingleton(classType);

        if (classInstance == null)
            return;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.appendFormattedLine("{0}: {1} | {2}: {3}",
                                          "Instance".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                    .applyAttribute(Attribute.UNDERLINE),
                                          classType.getName()
                                                .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                          "Hash Code".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                     .applyAttribute(Attribute.UNDERLINE),
                                          Integer.toHexString(classInstance.hashCode())
                                                 .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA))
                     .appendFormattedLine("{0}: {1} | {2}: {3}",
                                          "Field".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                 .applyAttribute(Attribute.UNDERLINE),
                                          field.getName()
                                               .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                          "Type".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                .applyAttribute(Attribute.UNDERLINE),
                                          field.getType()
                                               .getName()
                                               .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));


        Object fieldValue = DependencyInjectionContainer.retrieve(field.getType());

        if (fieldValue == null)
            return;

        stringBuilder.appendFormattedLine("{0}: {1} | {2}: {3}",
                                          "Assign Instance".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                           .applyAttribute(Attribute.UNDERLINE),
                                          fieldValue.getClass()
                                                    .getName()
                                                    .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                          "Hash Code".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                     .applyAttribute(Attribute.UNDERLINE),
                                          Integer.toHexString(fieldValue.hashCode())
                                                 .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));

        field.setAccessible(true);

        field.set(classInstance, fieldValue);

        System.out.print(stringBuilder.appendSeparator());
    }

}
