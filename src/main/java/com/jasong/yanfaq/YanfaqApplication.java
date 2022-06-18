package com.jasong.yanfaq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class YanfaqApplication {

    public static void main(String[] args) {
        SpringApplication.run(YanfaqApplication.class, args);
    }

}
