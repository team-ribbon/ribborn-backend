package com.spring.ribborn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import javax.annotation.PostConstruct;
//import java.util.TimeZone;

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class RibbornApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbornApplication.class, args);
	}

}
