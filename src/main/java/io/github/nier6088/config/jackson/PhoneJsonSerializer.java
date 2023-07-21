package io.github.nier6088.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/5/16-16:53
 */

public class PhoneJsonSerializer extends JsonSerializer {
    private final String regex = "(13[0-9]|14[5-9]|15[0-35-9]|16[25-7]|17[0-8]|18[0-9]|19[0135689])\\d{4}(\\d{4})";

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String phone = null;
        if (value instanceof String) {
            phone = (String) value;
            if (StringUtils.isNotBlank(phone)) {
                String result = phone.replaceAll(regex, "$1****$2");
                gen.writeString(result);
            } else {
                gen.writeObject(value);
            }
        } else {
            gen.writeObject(value);
        }
    }
}
