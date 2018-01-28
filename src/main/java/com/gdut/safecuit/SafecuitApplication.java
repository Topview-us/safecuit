package com.gdut.safecuit;

import com.gdut.safecuit.common.properties.druid.DruidDBConfig;
import com.gdut.safecuit.device.common.util.DataTreeCacheTask;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.gdut.safecuit") //扫描mapper
@EnableConfigurationProperties({DruidDBConfig.class})
@ServletComponentScan //扫描filter
public class SafecuitApplication {

	public static void main(String[] args) {

		SpringApplication.run(SafecuitApplication.class, args);

		//开启定时清理缓存的线程
		new DataTreeCacheTask().run();
	}
}
