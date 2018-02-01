package com.belatrix.moneyxchange.api.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.belatrix.moneyxchange.api")
@SpringBootApplication
@EnableJpaRepositories("com.belatrix.moneyxchange.api")
@EnableScheduling
@EntityScan("com.belatrix.moneyxchange.api")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}