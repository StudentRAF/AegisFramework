package rs.raf.student.aegisframework.core.dependency_injection;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class DependencyInjectionContainer {

    private static final Set<Class<?>>           prototypeMap = Sets.newHashSet();
    private static final Map<Class<?>, Object>   singletonMap  = Maps.newHashMap();
    private static final Map<Class<?>, Class<?>> superClassMap = Maps.newHashMap();

    public static <Type> void registerPrototype(Class<Type> classType) {
        prototypeMap.add(classType);

        System.out.print(new StringBuilder().appendFormattedLine("{0}:          {1}",
                                                                 "Register Prototype".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                     .applyAttribute(Attribute.UNDERLINE),
                                                                 classType.getName()
                                                                          .applyColorAttribute(Attribute.SET_FOREGROUND, Color.LIME)));

        registerSuperClasses(classType.getSuperclass(), classType);

        Arrays.stream(classType.getInterfaces())
              .forEach(superInterface -> registerInterface(superInterface, classType));
    }

    @SneakyThrows
    public static <Type> void registerSingleton(Class<Type> concreteClass) {
        if (singletonMap.containsKey(concreteClass))
            throw new RuntimeException("Cannot register multiple singletons! Class: " + concreteClass.getName());

        singletonMap.put(concreteClass, concreteClass.getDeclaredConstructor().newInstance());

        System.out.print(new StringBuilder().appendFormattedLine("{0}:          {1}",
                                                                 "Register Singleton".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                     .applyAttribute(Attribute.UNDERLINE),
                                                                 concreteClass.getName()
                                                                          .applyColorAttribute(Attribute.SET_FOREGROUND, Color.BLUE)));

        registerSuperClasses(concreteClass.getSuperclass(), concreteClass);

        Arrays.stream(concreteClass.getInterfaces())
              .forEach(superInterface -> registerInterface(superInterface, concreteClass));
    }

    private static <Type> void registerSuperClasses(Class<Type> superClass, Class<? extends Type> concreteClass) {
        if (superClass.equals(Object.class))
            return;

        registerSuperClass(superClass, concreteClass);

        Arrays.stream(superClass.getInterfaces())
              .forEach(superInterface -> registerInterface(superInterface, concreteClass));

        registerSuperClasses(superClass.getSuperclass(), concreteClass);
    }

    public static void registerSuperClass(Class<?> superClass, Class<?> concreteClass) {
        if (superClassMap.get(superClass) == null)
            superClassMap.put(superClass, concreteClass);
        else
            superClassMap.put(superClass, Object.class);

        System.out.print(new StringBuilder().appendFormattedLine("{0}: {1} {2} {3}",
                                                                 "Register Super Class Mapper".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                              .applyAttribute(Attribute.UNDERLINE),
                                                                 superClass.getName()
                                                                           .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                                                 "->".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER),
                                                                 superClassMap.get(superClass).getName()
                                                                              .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA)));
    }

    private static void registerInterface(Class<?> interfaceClass, Class<?> concreteClass) {
        if (superClassMap.get(interfaceClass) == null)
            superClassMap.put(interfaceClass, concreteClass);
        else
            superClassMap.put(interfaceClass, Object.class);

        System.out.print(new StringBuilder().appendFormattedLine("{0}:   {1} {2} {3}",
                                                                 "Register Interface Mapper".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                            .applyAttribute(Attribute.UNDERLINE),
                                                                 interfaceClass.getName()
                                                                               .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                                                 "->".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER),
                                                                 superClassMap.get(interfaceClass).getName()
                                                                              .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA)));

        Arrays.stream(interfaceClass.getInterfaces())
              .forEach(superInterface -> registerInterface(superInterface, concreteClass));
    }

    @SneakyThrows
    public static <Type> Type retrieve(Class<Type> typeClass) {
        if (singletonMap.containsKey(typeClass))
            return (Type) singletonMap.get(typeClass);;

        if (prototypeMap.contains(typeClass)) {
            Type prototypeInstance = typeClass.getDeclaredConstructor().newInstance();

            Arrays.stream(prototypeInstance.getClass()
                                           .getDeclaredFields())
                  .forEach(field -> {
                      try {
                          Object fieldInstance = retrieve(field.getType());

                          field.setAccessible(true);
                          field.set(prototypeInstance, fieldInstance);
                      }
                      catch (Exception ignored) { }
                  });

            return prototypeInstance;
        }

        if (superClassMap.containsKey(typeClass)) {
            Class<?> dependencyType = superClassMap.get(typeClass);

            if (dependencyType.equals(Object.class)) {
                System.out.println("Class: " + typeClass.getName() + " has multiple qualifiers!");
                return null;
            }

            return (Type) retrieve(dependencyType);
        }

        throw new RuntimeException("Class: " + typeClass.getName() + " is not Aegis Bean!");
    }

    public static Object getSingleton(Class<?> singletonClass) {
        return singletonMap.get(singletonClass);
    }

    public static void logState() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.appendFormattedLine("     {0}",
                                          "Prototype".applyColorAttribute(Attribute.SET_FOREGROUND, Color.NAVY))
                     .appendSeparatorWide();

        prototypeMap.stream()
                    .map(DependencyInjectionContainer::retrieve)
                    .filter(Objects::nonNull)
                    .forEach(instance -> logInstanceAndFields(instance, stringBuilder));

        stringBuilder.appendSeparatorWide()
                     .appendFormattedLine("     {0}",
                                          "Singleton".applyColorAttribute(Attribute.SET_FOREGROUND, Color.NAVY))
                     .appendSeparatorWide();

        singletonMap.keySet()
                    .stream()
                    .map(DependencyInjectionContainer::retrieve)
                    .filter(Objects::nonNull)
                    .forEach(instance -> logInstanceAndFields(instance, stringBuilder));

        stringBuilder.appendSeparatorWide()
                     .appendFormattedLine("     {0}",
                                          "Super Class".applyColorAttribute(Attribute.SET_FOREGROUND, Color.NAVY))
                     .appendSeparatorWide();
        superClassMap.keySet()
                     .forEach(superClassEntry -> logSuperClassDependency(superClassEntry, superClassMap.get(superClassEntry), stringBuilder));

        System.out.print(stringBuilder);
    }

    private static void logInstanceAndFields(Object instance, StringBuilder stringBuilder) {
        stringBuilder.appendFormattedLine("{0}: {1} | {2}: {3}",
                                          "Instance".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                    .applyAttribute(Attribute.UNDERLINE),
                                          instance.getClass()
                                                  .getName()
                                                  .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                          "Hash Code".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                     .applyAttribute(Attribute.UNDERLINE),
                                          Integer.toHexString(instance.hashCode())
                                                 .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));

        Arrays.stream(instance.getClass()
                              .getDeclaredFields())
              .forEach(field -> {
                  stringBuilder.appendFormatted("{0}: {1} | ",
                                                "Field".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                       .applyAttribute(Attribute.UNDERLINE),
                                                field.getName()
                                                     .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL));
                  field.setAccessible(true);

                  try {
                      Object fieldInstance = field.get(instance);

                      if (fieldInstance != null)
                          stringBuilder.appendFormattedLine("{0}: {1} | {2}: {3}",
                                                            "Instance Type".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                           .applyAttribute(Attribute.UNDERLINE),
                                                            fieldInstance.getClass()
                                                                         .getName()
                                                                         .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA),
                                                            "Hash Code".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                       .applyAttribute(Attribute.UNDERLINE),
                                                            Integer.toHexString(fieldInstance.hashCode())
                                                                   .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));
                      else
                          stringBuilder.appendFormattedLine("{0}",
                                                            "null".applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));
                  }
                  catch (IllegalAccessException ignore) { }
              });

        stringBuilder.appendSeparator();
    }

    private static void logSuperClassDependency(Class<?> superClass, Class<?> instanceClass, StringBuilder stringBuilder) {
        stringBuilder.appendFormattedLine("{0}: {1} | {2}: {3}",
                                          "Super Class Type".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                            .applyAttribute(Attribute.UNDERLINE),
                                          superClass.getName()
                                                    .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA),
                                          "Instance Type".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                         .applyAttribute(Attribute.UNDERLINE),
                                          instanceClass.getName()
                                                       .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA))
                     .appendSeparator();
    }

}
