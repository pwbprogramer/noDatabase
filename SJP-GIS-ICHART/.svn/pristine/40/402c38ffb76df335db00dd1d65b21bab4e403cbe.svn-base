package com.berheley.ichart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
@Configuration
@SpringBootApplication
@ComponentScan("com.berheley.ichart")
@EnableAutoConfiguration
public class SjpGisIchartApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SjpGisIchartApplication.class, args);
	}
	
	@Bean
	@RequestMapping("/")
    public String toIndex() {
        return "forward:/se7en/login1";
    }
}
