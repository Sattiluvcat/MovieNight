package com.satti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
@EnableCaching
public class MovieNightApplication {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(MovieNightApplication.class, args);
    }

}
