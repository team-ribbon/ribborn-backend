package com.spring.ribborn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//import javax.annotation.PostConstruct;
//import java.util.TimeZone;

@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableJpaRepositories
@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class})
public class RibbornApplication {

	@PostConstruct
	public void timeZone() {
		// timezone Asia/Seoul 셋팅
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}


	public static void main(String[] args) {
		SpringApplication.run(RibbornApplication.class, args);
	}



}
