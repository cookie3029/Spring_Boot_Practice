package com.kabigon;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.springframework.boot.SpringApplication.*;

@EnableJpaAuditing
@SpringBootApplication
public class MyProjectApplication {

	public static void main(String[] args) {
		run(MyProjectApplication.class, args);
	}

}
