package com.akash.application.controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.akash.application.service.ServiceConcept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
public class ControllerConcept {
	@Autowired
	private ServiceConcept serviceConcept;
	@GetMapping("/hello")
	public String hello() {
		return "Hello, Spring Boot Controller class invoked "+serviceConcept.getGreetingMessage();
	}

	@GetMapping("/time")
	public String getCurrentTime() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return LocalDateTime.now().format(formatter);
	}

	@GetMapping("/ask")
	public String ask(@RequestParam String strQuery, @RequestParam String sessionId) throws Exception {
		System.out.println("User Query : "+strQuery);
		System.out.println("sessionId : "+sessionId);
		System.out.println("Query Response : "+serviceConcept.askAI(strQuery,sessionId));
		return serviceConcept.askAI(strQuery,sessionId);
	}

	@GetMapping("/ready")
	public String getReady() {
			return "I am , are you?";
	}

}