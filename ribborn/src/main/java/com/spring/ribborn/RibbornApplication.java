package com.spring.ribborn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@ComponentScan
@EnableJpaRepositories
@SpringBootApplication
public class RibbornApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbornApplication.class, args);
	}

}
