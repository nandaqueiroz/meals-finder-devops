package br.edu.ufscar.backend.mealsfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MealsFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealsFinderApplication.class, args);
    }
}
