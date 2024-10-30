package rs.raf.student.aegisframework.core.scanner;

import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.core.annotation.AegisLibrary;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class AnnotationScanner {

    private static final Set<Class<?>> libraryClassSet = Sets.newHashSet();

    @SneakyThrows
    public static void scan() {
        scanLibraries();
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

    public static List<Class<?>> getLibraryClasses() {
        return libraryClassSet.stream()
                              .toList();
    }

}
