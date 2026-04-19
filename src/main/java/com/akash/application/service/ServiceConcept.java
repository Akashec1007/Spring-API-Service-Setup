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
	@Value("${gemini.imageApi.url}")
	private String strImageApiUrl;
	public String generateImageHtml(String prompt) throws Exception {
		String body = """
		{
			"contents": [
			{
				"parts": [
				{ "text": "%s" }
				]
			}
			],
			"generationConfig": {
			"responseModalities": ["TEXT", "IMAGE"]
			}
		}
		""".formatted(prompt);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(strImageApiUrl + strApiKey))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject json = new JSONObject(response.body());
		JSONArray parts = json
				.getJSONArray("candidates")
				.getJSONObject(0)
				.getJSONObject("content")
				.getJSONArray("parts");
		String base64Image = null;
		for (int i = 0; i < parts.length(); i++) {
			JSONObject part = parts.getJSONObject(i);
	
			if (part.has("inlineData")) {
				base64Image = part
						.getJSONObject("inlineData")
						.getString("data");
				break;
			}
		}
		if (base64Image == null) {
			return "<h3>No image returned</h3>";
		}
		return """
		<html>
		<body style="text-align:center;background:#111;color:white;">
			<h2>Generated Image</h2>
			<img style="max-width:80%%;border:2px solid white;"
					src="data:image/png;base64,%s"/>
		</body>
		</html>
		""".formatted(base64Image);
	}
}