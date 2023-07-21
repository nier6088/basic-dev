package io.github.nier6088.config.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;

/**
 * 添加返回结果中自动Long转String
 *
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/5/16-9:47
 */
@Configuration
public class GlobalLongToStringJsonModuleConfig extends SimpleModule {
    public GlobalLongToStringJsonModuleConfig() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
    }


}
