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
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/hello";
		String response = restTemplate.getForObject(url, String.class);
		System.out.println("Spring Boot Application Self Call Response for /hello endpoint "+response);
		url = "http://localhost:8080/";
		response = restTemplate.getForObject(url, String.class);
		System.out.println("Spring Boot Application Self Call Response for / endpoint "+response);
		url = "http://localhost:8080/time";
		response = restTemplate.getForObject(url, String.class);
		System.out.println("Spring Boot Application Self Call Response for /time endpoint "+response);
	}
}