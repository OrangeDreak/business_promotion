package com.x.bp.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Configuration
@MapperScan("com.x.bp.dao.mapper")
public class MybatisDataSource {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }
}
