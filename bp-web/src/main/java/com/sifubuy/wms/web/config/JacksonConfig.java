package com.sifubuy.wms.web.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 系统信息配置
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
                //.serializerByType(Long.TYPE, ToStringSerializer.instance)
                //.serializerByType(Long.class, ToStringSerializer.instance)
                .createXmlMapper(false)
                .build();
        // 当接收到未定义的枚举类型时, 将枚举字段设为NULL
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        // 设置时区
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 设置日期格式
        mapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        // 序列化成时间戳时间
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        return mapper;
    }


}
