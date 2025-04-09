package org.example.aiprojectapril;

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
        ChatGPTRequest.Message systemMessage = new ChatGPTRequest.Message("system",
                "Du er en rejseplanlægger. Dine svar skal følge et præcist og konsekvent format.\n\n" +

                        "1. Start med en overskrift:\n" +
                        "Byer i rejsen:\n" +
                        "- [by 1]\n" +
                        "- [by 2]\n" +
                        "- [by 3]\n" +
                        "(kun bynavne – ingen lande, restauranter, bygninger eller madretter)\n\n" +

                        "2. Derefter skriv sætningen:\n" +
                        "Rejsen går til [overordnet destination].\n\n" +

                        "3. Følg op med en dag-til-dag plan. Hver dag eller gruppe af dage skal følge formatet:\n" +
                        "Dag X-Y: [aktiviteter] i [bynavn], [land].\n" +
                        "Beskriv aktiviteter, lokale oplevelser og gerne anbefalede retter eller restauranter (kun i denne del).\n\n" +

                        "4. Afslut med et budget, der ser sådan ud:\n" +
                        "Transport: X DKK\n" +
                        "Overnatning: Y DKK\n" +
                        "Mad og oplevelser: Z DKK\n" +
                        "Samlet budget: SUM DKK\n\n" +

                        "Bemærk:\n" +
                        "- Byer i listen i toppen bruges til at vise et kort, så skriv kun reelle byer som findes på Google Maps.\n" +
                        "- Brug ikke lande, bygninger, mad eller fiktive navne i bylisten.\n" +
                        "- Alt hvad der vises i punktlisten må kun være navnet på en by.\n"
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
