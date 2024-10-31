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
        if (singletonMap.containsKey(typeClass)) {
            Object singletonInstance = singletonMap.get(typeClass);

            logInstance(singletonInstance);

            return (Type) singletonInstance;
        }

        if (prototypeMap.contains(typeClass)) {
            Type prototypeInstance = typeClass.getDeclaredConstructor().newInstance();

            Arrays.stream(prototypeInstance.getClass()
                                           .getFields())
                  .forEach(field -> {
                      try {
                          Object fieldInstance = retrieve(field.getType());

                          field.setAccessible(true);
                          field.set(prototypeInstance, fieldInstance);
                      }
                      catch (Exception ignored) { }
                  });

            logInstance(prototypeInstance);

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

    private static void logInstance(Object object) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.appendFormattedLine("{0}: {1} | {2}: {3}",
                                          "Object".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                  .applyAttribute(Attribute.UNDERLINE),
                                          object.getClass().getName()
                                                .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL),
                                          "Hash Code".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                     .applyAttribute(Attribute.UNDERLINE),
                                          Integer.toHexString(object.hashCode())
                                                 .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));
        Arrays.stream(object.getClass()
                            .getDeclaredFields())
              .forEach(field -> {
                  field.setAccessible(true);

                  stringBuilder.appendFormatted("{0}: {1} | ",
                                                "Field".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                       .applyAttribute(Attribute.UNDERLINE),
                                                field.getName()
                                                     .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL));

                  try {
                      Object fieldValue = field.get(object);
                      stringBuilder.appendFormattedLine("{0}: {1}",
                                                        "Value".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                          .applyAttribute(Attribute.UNDERLINE),
                                                        (fieldValue == null ? "null" : fieldValue.toString())
                                                                  .applyColorAttribute(Attribute.SET_FOREGROUND, Color.AQUA));
                  }
                  catch (IllegalAccessException ignore) {
                      stringBuilder.appendFormattedLine("{0}", "Error getting field's value.".applyColorAttribute(Attribute.SET_FOREGROUND, Color.RED));
                  }
              });

        System.out.print(stringBuilder.appendSeparator());
    }

}
