package org.example.aiprojectapril;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class AiProjectApplication implements CommandLineRunner {
    // Henter API-nøglen fra application.properties og gemmer den i variablen "key"
    @Value("${api.key}")
    private String key;

    public static void main(String[] args) {
        SpringApplication.run(AiProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*WebClient webClient = WebClient.create("https://api.openai.com");

        ChatGPTRequest request = new ChatGPTRequest(
                "gpt-3.5-turbo",
                List.of(new ChatGPTRequest.Message("user", "Plan a trip")),
                1, // temperature (kreativitet)
                1,
                1,
                // top_p (diversitet) (maksimal variation)
        );

        // Sender POST-request til OpenAI's /chat/completions endpoint
        webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", key)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class) // konverter JSON-svar til Java-objekt
                .subscribe(response -> {
                    // Når vi får et svar, udskriv det første svar fra GPT
                    String content = response.getChoices().get(0).getMessage().getContent();
                    System.out.println("ChatGPT Response: " + content);
                });
        */
    }
}
