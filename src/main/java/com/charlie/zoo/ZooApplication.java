package com.charlie.zoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ZooApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooApplication.class, args);
	}

}
