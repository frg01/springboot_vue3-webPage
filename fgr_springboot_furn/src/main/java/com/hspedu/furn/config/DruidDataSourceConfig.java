package com.hspedu.furn.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
@Configuration
@Slf4j
public class DruidDataSourceConfig {

    //配置 注入DruidDataSource
    //注入指定数据源就替换了默认数据源
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        log.info("数据源={}" ,druidDataSource.getClass());
        return druidDataSource;
    }
}
