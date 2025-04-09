package org.example.aiprojectapril;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping // Håndterer POST-requests til /chat
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");  // Henter beskeden fra request body
        String reply = chatService.getReplyFromOpenAI(userMessage); // Sender beskeden til GPT
        return ResponseEntity.ok(Map.of("reply", reply));  // Returnerer svaret som JSON
    }
}
