package com.akash.application.controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.akash.application.service.ServiceConcept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ControllerConcept {
	@Autowired
	private ServiceConcept serviceConcept;
	@GetMapping("/hello")
	public String hello() {
		return "Hello, Spring Boot Controller class invoked"+serviceConcept.getGreetingMessage();
	}

	@GetMapping("/")
	public String pingTest() {
		return "Service is up and running.";
	}
}