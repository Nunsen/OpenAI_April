package org.example.aiprojectapril;

import java.util.List;

public class ChatGPTRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private double top_p;
    private int n;
    private int max_tokens;

    // Opdateret constructor med nye felter
    public ChatGPTRequest(String model, List<Message> messages, double temperature, double top_p, int n, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.top_p = top_p;
        this.n = n;
        this.max_tokens = max_tokens;
    }

    // Getters og setters for alle felter
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getTop_p() { return top_p; }
    public void setTop_p(double top_p) { this.top_p = top_p; }

    public int getN() { return n; }
    public void setN(int n) { this.n = n; }

    public int getMax_tokens() { return max_tokens; }
    public void setMax_tokens(int max_tokens) { this.max_tokens = max_tokens; }

    // Indre klasse til messages
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}
