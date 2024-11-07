package rs.raf.student.aegisframework.web.endpoint;

import com.google.common.collect.Maps;
import lombok.experimental.ExtensionMethod;
import rs.raf.student.aegisframework.utils.ansi.Attribute;
import rs.raf.student.aegisframework.utils.ansi.Color;
import rs.raf.student.aegisframework.utils.extension.StringANSIEscapeExtension;
import rs.raf.student.aegisframework.utils.extension.StringBuilderExtension;
import rs.raf.student.aegisframework.web.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

@ExtensionMethod({StringBuilderExtension.class, StringANSIEscapeExtension.class})
public class EndpointManager {

    private static final Map<Class<? extends Annotation>, HttpMethod> requestAnnotationMap = Maps.newHashMap();

    public static <Type extends Annotation> void registerRequestAnnotation(AbstractRequestAnnotation<Type> requestAnnotation) {
        requestAnnotationMap.put(requestAnnotation.getAnnotationClass(), requestAnnotation.getHttpMethod());

        System.out.print(new StringBuilder().appendFormattedLine("{0}:    {1}",
                                                                 "Register Inject Annotation".applyColorAttribute(Attribute.SET_FOREGROUND, Color.SILVER)
                                                                                             .applyAttribute(Attribute.UNDERLINE),
                                                                 requestAnnotation.getAnnotationClass()
                                                                                  .getName()
                                                                                  .applyColorAttribute(Attribute.SET_FOREGROUND, Color.TEAL)));
    }

    public static boolean isRequestAnnotation(Class<?> typeClass) {
        return requestAnnotationMap.containsKey(typeClass);
    }

    public static boolean hasRequestAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                     .anyMatch(annotation -> requestAnnotationMap.containsKey(annotation.annotationType()));
    }

    public static HttpMethod getHttpMethod(Class<? extends Annotation> annotationType) {
        return requestAnnotationMap.get(annotationType);
    }

}
