package rs.raf.student.aegisframework.web.endpoint;

import rs.raf.student.aegisframework.utils.Pair;
import rs.raf.student.aegisframework.web.annotation.Path;
import rs.raf.student.aegisframework.web.http.HttpMethod;
import rs.raf.student.aegisframework.web.scanner.WebScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class EndpointFactory {

    //region Initialise Container

    public static void initialiseContainer() {
        WebScanner.controllerClasses()
                  .stream()
                  .<Pair<String, Method>>mapMulti((classType, consumer) -> Arrays.stream(classType.getDeclaredMethods())
                                                                                 .forEach(method -> consumer.accept(Pair.of(controllerRootPath(classType), method))))
                  .filter(pair -> EndpointManager.hasRequestAnnotation(pair.getValue().getDeclaredAnnotations()))
                  .map(pair -> Pair.of(concatenatePath(pair.getKey(), pair.getValue()), pair.getValue()))
                  .forEach(pair -> EndpointContainer.registerEndpoint(pair.getKey(), findHttpMethod(pair.getValue()), pair.getValue()));
    }

    private static String controllerRootPath(Class<?> typeClass) {
        if (!typeClass.isAnnotationPresent(Path.class))
            return "";

        return typeClass.getAnnotation(Path.class).value();
    }

    private static String concatenatePath(String path, Method method) {
        if (!method.isAnnotationPresent(Path.class))
            return path;

        return path + method.getAnnotation(Path.class).value();
    }

    private static HttpMethod findHttpMethod(Method method) {
        for (Annotation annotation : method.getAnnotations())
            if (EndpointManager.isRequestAnnotation(annotation.annotationType()))
                return EndpointManager.getHttpMethod(annotation.annotationType());

        return null;
    }

    //endregion Initialise Container

}
