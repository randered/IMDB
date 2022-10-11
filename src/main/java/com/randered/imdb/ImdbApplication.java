package com.randered.imdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableSwagger2
public class ImdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbApplication.class, args);
    }

}
