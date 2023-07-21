package io.github.nier6088.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

public class ObjectMapperUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        if (SpringContextUtil.isDeveloperMode()) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
    }

    @SneakyThrows
    public static <T> String toStr(T t) {
        return objectMapper.writeValueAsString(t);
    }

    @SneakyThrows
    public static <T> T toObject(String content, Class<T> clazz) {
        return objectMapper.readValue(content, clazz);
    }
}
