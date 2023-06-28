package com.sparta.hanghaeblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaeBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanghaeBlogApplication.class, args);
	}

}
