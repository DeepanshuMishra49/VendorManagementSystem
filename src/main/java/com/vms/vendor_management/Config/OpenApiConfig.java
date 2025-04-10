//package com.vms.vendor_management.Config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Vendor Management System API")
//                        .description("API documentation for Vendor Management System")
//                        .version("1.0")
//                        .contact(new Contact()
//                                .name("Your Name")
//                                .email("your.email@example.com")
//                                .url("https://your-website.com"))
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url("http://www.apache.org/licenses/LICENSE-2.0")))
//                .components(new Components()
//                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")));
//    }
//}