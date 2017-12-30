package org.mjjaen.rest.apidocument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApidocumentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApidocumentApplication.class, args);
	}
}
