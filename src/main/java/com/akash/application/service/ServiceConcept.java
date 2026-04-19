package com.akash.application.service;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
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
		String responseBody = response.body();
		System.out.println("responseBody------------------------->"+responseBody);
		if (response.statusCode() != 200) {
			return responseBody;
		}
		JSONObject json = new JSONObject(responseBody);
		String strAIReply = "";
		if (json.has("candidates")) {
			JSONArray candidates = json.getJSONArray("candidates");
			if (candidates.length() > 0) {
				JSONObject content = candidates
						.getJSONObject(0)
						.getJSONObject("content");
				JSONArray parts = content.getJSONArray("parts");
				for (int i = 0; i < parts.length(); i++) {
					JSONObject part = parts.getJSONObject(i);
					if (part.has("text")) {
						strAIReply = part.getString("text");
						break;
					}
				}
			}
		}
		return strAIReply;
	}
}