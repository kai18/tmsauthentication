package com.training.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = "com.training.jwt.repository")
@ComponentScan({ "com.training.jwt" })
@EntityScan("com.training.jwt.model")
public class TmsAuthenticationApp {

	public static void main(String[] args) {
		SpringApplication.run(TmsAuthenticationApp.class, args);
	}
}