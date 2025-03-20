package main.java.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Service1App {
    public static void main(String[] args) {
        SpringApplication.run(Service1App.class, args);
    }
}