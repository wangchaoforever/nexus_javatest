package com.hgb.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 行程微服务
 * @author Guobin.Hu
 * @date 2018/11/27 12:27
 */
@SpringBootApplication(scanBasePackages = {"com.hgb.utils"})
public class PlatformHgbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformHgbApplication.class, args);
	}
}
