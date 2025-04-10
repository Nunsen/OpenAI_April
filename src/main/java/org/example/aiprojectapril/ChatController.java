package org.example.aiprojectapril;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(final ChatService chatService) {
        this.chatService = chatService;
    }

    //Vi har ændret fra message til messages, da vi vil have det som en interaktiv samtale der husker de forskellige messages
    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, Object> payload) {
        // Forventet struktur: { "messages": [ { "role": "user", "content": "..." }, ... ] }
        List<Map<String, String>> rawMessages = (List<Map<String, String>>) payload.get("messages");

        List<ChatGPTRequest.Message> messages = rawMessages.stream()
                .map(m -> new ChatGPTRequest.Message(m.get("role"), m.get("content")))
                .toList();

        String reply = this.chatService.getReplyFromOpenAI(messages);
        return ResponseEntity.ok(Map.of("reply", reply));
    }
}
