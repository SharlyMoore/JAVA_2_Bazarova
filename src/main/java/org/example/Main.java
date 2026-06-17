package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        log.info("Программа запущена");
        
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("Дашик", 20);
        String json = mapper.writeValueAsString(person);
        log.info("JSON: {}", json);
        
        Person deserialized = mapper.readValue(json, Person.class);
        log.info("Десериализовано: {} лет", deserialized.age);
        
        System.out.println("Данные переданы");
        log.info("Программа завершена");
    }
}