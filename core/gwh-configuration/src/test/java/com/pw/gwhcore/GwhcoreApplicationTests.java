package com.pw.gwhcore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GwhcoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@SpringBootApplication
	static class TestClass {

	}

	// @SpringBootApplication(scanBasePackages = "com.pw.gwhcore")
	// @EntityScan("com.pw.gwhcore")
    // @EnableJpaRepositories("com.pw.gwhcore")
    // @ComponentScan(basePackages = "com.pw.gwhcore")
	// static class TestClass {

	// }
}
