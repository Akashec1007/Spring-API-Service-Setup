package com.akash.application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
public class SpringbootApplicationConcept {
	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println("Spring Boot Application Execution Start Triggers @ "+LocalDateTime.now().format(formatter));
		SpringApplication.run(SpringbootApplicationConcept.class, args);
		System.out.println("Spring Boot Application Execution Started @ "+LocalDateTime.now().format(formatter));
	}
}