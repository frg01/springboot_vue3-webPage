package com.hspedu.furn.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
@Configuration
public class MybatisPlusConfig {

    //注入mybatisPusInterceptor 对象
    //mybatisPusInterceptor bean中插入分页插件 分页要指定数据库类型
    //通过拦截器在拦截器中在添加一个分页拦截器，经过拦截器后会继续经过分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
