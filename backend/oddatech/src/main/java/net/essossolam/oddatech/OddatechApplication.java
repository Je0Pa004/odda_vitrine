package net.essossolam.oddatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OddatechApplication {

    public static void main(String[] args) {
        SpringApplication.run(OddatechApplication.class, args);
    }

}
