package com.tinqinacademy.search.restexport;


import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestExportConfig {

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}
