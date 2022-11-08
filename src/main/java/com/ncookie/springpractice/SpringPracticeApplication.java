package com.ncookie.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing	// JPA Auditing 활성화를 위해
@SpringBootApplication
public class SpringPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeApplication.class, args);
	}

}
