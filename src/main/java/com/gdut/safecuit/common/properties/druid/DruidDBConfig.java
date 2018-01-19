package com.gdut.safecuit.common.properties.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Garson in 14:12 2018/1/19
 * Description :
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
@PropertySource("classpath:/application.properties")
public class DruidDBConfig {

		private String url;

		private String userName;

		private String passWord;

		private String driverClassName;

		private int initialSize;

		private int minIdle;

		private int maxActive;

		private int maxWait;

		private String filters;

		@Bean
		@Primary //有多个DataSource配置时优先注入这个
		public DataSource dataSource() {
			DruidDataSource dataSource = new DruidDataSource();
			dataSource.setUrl(url);
			dataSource.setUsername(userName);
			dataSource.setPassword(passWord);
			dataSource.setDriverClassName(driverClassName);

			//configuration
			dataSource.setInitialSize(initialSize);
			dataSource.setMinIdle(minIdle);
			dataSource.setMaxActive(maxActive);
			dataSource.setMaxWait(maxWait);
			try {
				dataSource.setFilters(filters);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return dataSource;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassWord() {
			return passWord;
		}

		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public int getInitialSize() {
			return initialSize;
		}

		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}

		public String getFilters() {
			return filters;
		}

		public void setFilters(String filters) {
			this.filters = filters;
		}

}
