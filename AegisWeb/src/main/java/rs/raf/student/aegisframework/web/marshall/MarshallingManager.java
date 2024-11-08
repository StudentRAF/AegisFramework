package rs.raf.student.aegisframework.web.marshall;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class MarshallingManager {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @SneakyThrows
    public static String toJson(Object object ) {
        if (object == null)
            return "";

        return jsonMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static <Type> Type fromJson(String json, Class<Type> typeClass) {
        return jsonMapper.readValue(json, typeClass);
    }

}
