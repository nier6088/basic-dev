package io.github.nier6088;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.github.nier6088")
@MapperScan("io.github.nier6088.mapper")
public class SimpleProjectApplicationAutoConfiguration {
}
