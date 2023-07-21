package io.github.nier6088.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.nier6088.constants.DbStatusConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @projectName: miquan-saas
 * @package: com.boolib.simple.common.config
 * @className: MybatisPlusConfig
 * @author: yangjun
 * @description:
 * @date: 2023/5/11 11:15
 * @version: 1.0
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"com.boolib.simple.mapper"})
public class GlobalMybatisPlusConfig implements MetaObjectHandler {


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        String property = metaObject.findProperty("record_status", true);
        if (StringUtils.isNotBlank(property)) {
            this.strictInsertFill(metaObject, property, Integer.class, DbStatusConstant.RECORD_STATUS_NORMAL);
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("modifyTime", new Date(), metaObject);
    }

}
