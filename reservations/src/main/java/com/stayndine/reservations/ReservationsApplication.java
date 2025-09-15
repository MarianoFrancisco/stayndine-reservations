package com.stayndine.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ReservationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationsApplication.class, args);
    }

}
