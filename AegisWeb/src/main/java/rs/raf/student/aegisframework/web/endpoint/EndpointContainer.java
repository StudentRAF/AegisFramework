package rs.raf.student.aegisframework.web.endpoint;

import com.google.common.collect.Maps;
import rs.raf.student.aegisframework.utils.Pair;
import rs.raf.student.aegisframework.web.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Map;

public class EndpointContainer {

    private static final Map<Pair<String, HttpMethod>, Method> endpointMethodMap = Maps.newHashMap();

    public static void registerEndpoint(String endpoint, HttpMethod httpMethod,  Method method) {
        endpointMethodMap.put(Pair.of(endpoint, httpMethod), method);
    }

}
