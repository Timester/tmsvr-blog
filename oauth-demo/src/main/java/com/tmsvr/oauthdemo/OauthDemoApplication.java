package com.tmsvr.oauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.tmsvr.oauthdemo.config")
public class OauthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthDemoApplication.class, args);
	}

}
