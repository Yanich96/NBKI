package org.example;

import org.example.loadtesting.LoadTesting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Main.class, args);

        Thread.sleep(5000);

        LoadTesting.testing();
    }
}