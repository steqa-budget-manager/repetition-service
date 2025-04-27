package ru.steqa.repetitionservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    private final String queueName;
    private final String exchangeName;

    public RabbitConfig(@Value("${spring.rabbitmq.task-queue}") String queueName, @Value("${spring.rabbitmq.custom-exchange}") String exchangeName) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
    }

    @Bean
    public Queue taskQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(
                exchangeName,
                "x-delayed-message",
                true,
                false,
                args
        );
    }

    @Bean
    public Binding delayedBinding(Queue taskQueue, CustomExchange delayedExchange) {
        return BindingBuilder
                .bind(taskQueue)
                .to(delayedExchange)
                .with(queueName)
                .noargs();
    }
}
