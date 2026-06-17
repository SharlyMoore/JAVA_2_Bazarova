package org.example.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class NotificationManager {
    private final Map<String, MessageService> messageServices;

    public NotificationManager(Map<String, MessageService> messageServices) {
        this.messageServices = messageServices;
    }

    public void notify(String message, String recipient, String serviceType) {
        MessageService service = messageServices.get(serviceType);
        if (service != null) {
            service.sendMessage(message, recipient);
        } else {
            System.out.println("Сервис '" + serviceType + "' не найден. Доступные: " + messageServices.keySet());
        }
    }
}