package com.core.bankassets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author fsy
 */
@SpringBootApplication
@ComponentScan("com.core")
public class BankassetsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankassetsApplication.class, args);
    }

}
