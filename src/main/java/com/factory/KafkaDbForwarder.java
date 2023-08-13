package com.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KafkaDbForwarder {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDbForwarder.class, args);
    }

}
