package com.electronicBE.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(info());
        docket.securityContexts(Arrays.asList(securityContexts()));
        docket.securitySchemes(Arrays.asList(SecuritySchemes()));
        ApiSelectorBuilder select = docket.select();

        select.apis(RequestHandlerSelectors.any());

        select.paths(PathSelectors.any());
        Docket build = select.build();
        return build;
    }

    private ApiKey SecuritySchemes() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContexts() {

        SecurityContext context = SecurityContext.builder()
                .securityReferences(getSecurityReferences())
                .build();

        return context;
    }

    private List<SecurityReference> getSecurityReferences() {

        AuthorizationScope[] scopes = {
                new AuthorizationScope("Global", "Access everything")
        };


        return Arrays.asList(new SecurityReference("JWT", scopes));
    }


    public ApiInfo info() {

        ApiInfo apiInfo = new ApiInfo(
                "Electornic Store Backend : APIS",
                "This is backend project created by ",
                "1.0.0v",
                "",
                new Contact("Chaitanya", "https://www.linkedin.com", "chaitanya.renuse17@gmail.com"),
                "Linces of api",
                "",
                new ArrayDeque<>()

        );
        return apiInfo;


    }


}
