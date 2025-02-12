package com.bluecloud.sqstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SqsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsTestApplication.class, args);
    }

}
