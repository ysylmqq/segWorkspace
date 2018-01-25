package com.springboot.javaconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * 这个类相当于spring当中的application.xml文件
 * 由于在spring3.x当中提出了使用Java注解的方式来注入容器，所以，在spring4.x当中就
 * 特别的提倡使用基于Java类的方式来进行注入IOC容器,减少配置文件的使用
 *
 */
@Configuration
// 通过该注解来表明该类是一个Spring的配置，相当于一个xml文件
@ComponentScan(basePackages = "com.springboot.javaconfig")
// 配置扫描包
@PropertySource(value = { "classpath:jdbc.properties" }, ignoreResourceNotFound = true)
public class SpringConfig {

    @Bean(name="userDao")
    // 通过该注解来表明是一个Bean对象，相当于xml中的<bean>
   //Bean的类型和方法的返回值相同， 也可以不指定name 默认为方法名
    public UserDAO getUserDAO() {
        return new UserDAO(); // 直接new对象做演示
    }

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
    	System.err.println("dataSource start ");
        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
        // 数据库驱动
        boneCPDataSource.setDriverClass(jdbcDriverClassName);
        // 相应驱动的jdbcUrl
        boneCPDataSource.setJdbcUrl(jdbcUrl);
        // 数据库的用户名
        boneCPDataSource.setUsername(jdbcUsername);
        // 数据库的密码
        boneCPDataSource.setPassword(jdbcUsername);
        // 检查数据库连接池中空闲连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0
        boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
        // 连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0
        boneCPDataSource.setIdleMaxAgeInMinutes(30);
        // 每个分区最大的连接数
        boneCPDataSource.setMaxConnectionsPerPartition(100);
        // 每个分区最小的连接数
        boneCPDataSource.setMinConnectionsPerPartition(5);
        return boneCPDataSource;
    }

}
