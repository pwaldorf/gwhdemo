package com.pw.gwhcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pw")
public class GwhcoreApplication {

    public static void main(String[] args) {
		SpringApplication.run(GwhcoreApplication.class, args);
    }
}
