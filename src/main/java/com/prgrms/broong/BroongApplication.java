package com.prgrms.broong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BroongApplication {

    public static void main(String[] args) {
        SpringApplication.run(BroongApplication.class, args);


    }

}
