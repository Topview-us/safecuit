package com.gdut.safecuit;

import com.gdut.safecuit.common.properties.druid.DruidDBConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.gdut.safecuit") //扫描mapper
@EnableConfigurationProperties({DruidDBConfig.class}) //配置类
@ServletComponentScan //扫描filter
public class SafecuitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafecuitApplication.class, args);
	}
}
