package org.mjjaen.rest.jpacrudrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpacrudrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpacrudrestapiApplication.class, args);
	}
}
