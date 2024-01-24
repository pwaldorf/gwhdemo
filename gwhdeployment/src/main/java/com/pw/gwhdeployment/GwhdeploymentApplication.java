package com.pw.gwhdeployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pw")
public class GwhdeploymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwhdeploymentApplication.class, args);
	}

}
