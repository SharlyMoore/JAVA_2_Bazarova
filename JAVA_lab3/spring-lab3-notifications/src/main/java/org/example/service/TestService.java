package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final MessageService messageService;

    public TestService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void test() {
        System.out.println("=== TestService: @Primary выбран ===");
        messageService.sendMessage("Test", "test@test.com");
    }
}