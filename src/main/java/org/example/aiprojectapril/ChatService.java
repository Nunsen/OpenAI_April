package org.example.aiprojectapril.service;

import org.example.aiprojectapril.ChatGPTRequest;
import org.example.aiprojectapril.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ChatService {

    private final WebClient webClient;


    // Constructor - opretter en WebClient med base-URL og API-nøgle til OpenAI
    public ChatService(@Value("${api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", apiKey)
                .build();
    }


    // Metode der sender brugerens input til OpenAI og returnerer AI'ens svar som tekst
    public String getReplyFromOpenAI(String userInput) {
        // Systemrolle: styr AI’en
        ChatGPTRequest.Message systemMessage = new ChatGPTRequest.Message(
                "system",
                "Du er en rejseplanlægger. Du skal hjælpe brugeren med at planlægge rejser ud fra input om varighed, interesser, budget og rejsetype (solo, familie, vennegruppe). Svar med en dag-til-dag plan og et budget med forventede rejseomkostninger herunder fly, hotel, mad og aktivitets omkostninger."
        );

        // Brugerens input – det som personen har skrevet i frontend
        ChatGPTRequest.Message userMessage = new ChatGPTRequest.Message("user", userInput);

        // Byg hele requesten
        ChatGPTRequest request = new ChatGPTRequest(
                "gpt-3.5-turbo",
                List.of(systemMessage, userMessage),
                0.7,
                1,
                1,
                500

        );

        // Sender POST-request til OpenAI og får svar som ChatGPTResponse
        ChatGPTResponse response = webClient.post()
                .uri("/v1/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .block();

        return response.getChoices().get(0).getMessage().getContent();
    }
}
