package com.tradefederation.wholesaler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableSwagger2
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket wholesalerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wholesaler-api")
                .apiInfo(wholesalerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tradefederation.wholesaler"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private ApiInfo wholesalerApiInfo() {
        return new ApiInfoBuilder()
                .title("Wholesaler API")
                .build();
    }
}
