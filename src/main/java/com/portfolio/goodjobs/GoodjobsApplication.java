package com.portfolio.goodjobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoodjobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodjobsApplication.class, args);
    }

}
