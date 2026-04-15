package com.akash.application.service;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ServiceConcept {
	public String getGreetingMessage() {
		return "Hello, Spring Boot Service Class Reached";
	}
	@Value("${gemini.api.key}")
	private String strApiKey;
	@Value("${gemini.api.url}")
	private String strApiUrl;
	public String askAI(String prompt) throws Exception {
		String body = """
			{
			"contents": [
				{
				"parts": [
					{"text": "%s"}
				]
				}
			]
			}
			""".formatted(prompt);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(strApiUrl + strApiKey))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(body))
					.build();
	
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
	}

}