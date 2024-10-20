package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import foo.WelcomMessage;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.*"})
@EnableJpaRepositories(basePackages = {"com.example.demo.*"})
public class DemoApplication {

	public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "default");
		SpringApplication.run(DemoApplication.class, args);

        var welcomMessage = new WelcomMessage();
        System.out.println(welcomMessage.getWelcomeMessage());

	}

}
