package org.example.aiprojectapril;

import org.example.aiprojectapril.ChatGPTRequest;
import org.example.aiprojectapril.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.ArrayList;

@Service
public class ChatService {

    private final WebClient webClient;

    public ChatService(@Value("${api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", apiKey)
                .build();
    }

    // Nu modtager vi hele samtalen (List<Message>) fra frontend
    public String getReplyFromOpenAI(List<ChatGPTRequest.Message> userMessages) {
        ChatGPTRequest.Message systemMessage = new ChatGPTRequest.Message("system",
                "Du er en intelligent rejseplanlægger. Uanset hvad brugeren skriver, skal du altid svare med en opdateret rejseplan i følgende struktur:\n\n" +
                        "1. Start med overskriften:\n" +
                        "Byer i rejsen:\n" +
                        "- [by 1]\n" +
                        "- [by 2]\n" +
                        "- ...\n\n" +
                        "2. Skriv: Rejsen går til [destination].\n\n" +
                        "3. Lav dag-til-dag plan:\n" +
                        "Dag X-Y: [aktiviteter] i [bynavn], [land].\n" +
                        "Beskriv oplevelser, mad, lokale tips.\n\n" +
                        "4. Slut af med budget:\n" +
                        "Transport: X DKK\nOvernatning: Y DKK\nMad og oplevelser: Z DKK\nSamlet budget: SUM DKK\n\n" +
                        "Hvis brugeren giver feedback:\n" +
                        "- Anerkend og opsummer kort hvad brugeren ønsker ændret\n" +
                        "- Lav derefter en ny komplet rejseplan i samme format\n" +
                        "- Svar aldrig uden at holde dig til rejseemnet og strukturen\n"
        );

        // Saml systemrollen + samtalen
        List<ChatGPTRequest.Message> fullMessages = new ArrayList<>();
        fullMessages.add(systemMessage);
        fullMessages.addAll(userMessages);

        ChatGPTRequest request = new ChatGPTRequest(
                "gpt-3.5-turbo",
                fullMessages,
                0.7,
                1,
                1,
                700
        );

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
