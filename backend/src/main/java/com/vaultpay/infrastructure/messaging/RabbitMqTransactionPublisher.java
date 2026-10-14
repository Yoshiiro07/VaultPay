package com.vaultpay.infrastructure.messaging;

import com.vaultpay.domain.ports.TransactionPublisherPort;
import com.vaultpay.domain.event.TransactionDebitedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqTransactionPublisher implements TransactionPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${vaultpay.rabbitmq.exchange}")
    private String exchange;

    @Value("${vaultpay.rabbitmq.routingkey.debit}")
    private String routingKey;

    public RabbitMqTransactionPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishTransactionDebited(TransactionDebitedEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
        System.out.println(">>> [RABBITMQ] Evento de Débito publicado com sucesso: " + event.transactionId());
    }
}