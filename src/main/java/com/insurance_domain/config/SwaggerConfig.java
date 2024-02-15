package com.insurance_domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {

        return new ApiInfo("Insurance Project", "This project is developed by Basu", "1.0", "Terms of Service", new Contact("Basu", "https://javaSpringbootProject.com", "basu@gmail.com"), "License of Api", "Api license URL", Collections.emptyList());
    }
}
