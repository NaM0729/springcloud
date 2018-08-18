package com.demo.device_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.demo.device_server.dao")
@EnableDiscoveryClient
@EnableFeignClients
public class DeviceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceServerApplication.class, args);
	}
}
