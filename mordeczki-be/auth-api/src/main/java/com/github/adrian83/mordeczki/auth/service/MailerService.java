package com.github.adrian83.mordeczki.auth.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.model.entity.Account;



@Service
public class MailerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${kafka.topicRegisteredUser}")
    private String registeredUserTopic;

    public CompletableFuture<Account> accountRegistered(Account account) {
        return kafkaTemplate.send(registeredUserTopic, account)
                .completable()
                .thenApply(result -> account); // TODO return something else
    }


}

