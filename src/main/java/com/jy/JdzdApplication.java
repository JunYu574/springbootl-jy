package com.jy;

import com.jy.common.basic.dao.impl.BaseRepositoryImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: JunYu
 * @Date: 2024/1/26 19:40
 * @Description:
 * @Version: V1.0.0
 */
@EnableAsync
@EntityScan(basePackages = {"com.jy"})  //扫描@Entity注册
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = {"com.jy"})   //扫描@Repository注册
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = false)     //扫描带有 @Transactional 注解的方法，并在这些方法周围织入事务管理的逻辑
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@MapperScan("com.jy.*.mapper")
@SpringBootApplication(scanBasePackages = {"com.jy"})
public class JdzdApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdzdApplication.class);
    }
}
