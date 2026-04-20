package com.akash.application.service;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;
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
	private Map<String, List<Map<String, String>>> chatMemory = new HashMap<>();
	public String askAI(String prompt, String sessionId) throws Exception {
		List<Map<String, String>> messages = chatMemory.computeIfAbsent(sessionId, k -> new ArrayList<>());
		messages.add(Map.of("role", "user", "content", prompt));
		JSONArray msgArray = new JSONArray(messages);
		String body = new JSONObject().put("model", "llama-3.3-70b-versatile").put("messages", msgArray).toString();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(strApiUrl))
				.header("Authorization", "Bearer " + strApiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject json = new JSONObject(response.body());
		if (json.has("error")) {
			return "❌ " + json.getJSONObject("error").getString("message");
		}
		if (json.has("choices")) {
			JSONArray choices = json.getJSONArray("choices");
			if (choices.length() > 0) {
				String reply = json.getJSONArray("choices")
					.getJSONObject(0)
					.getJSONObject("message")
					.getString("content");
				messages.add(Map.of("role", "assistant", "content", reply));
				return reply;
			}
		}
		return "No response from AI";
	}
}