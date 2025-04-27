package ru.steqa.repetitionservice.utility;

import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TaskProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue taskQueue;
    private final CustomExchange customExchange;

    public TaskProducer(RabbitTemplate rabbitTemplate, Queue taskQueue, CustomExchange customExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.taskQueue = taskQueue;
        this.customExchange = customExchange;
    }

    public void sendTaskAtTime(String repetitionRuleId, LocalDateTime targetTime) {
        long delay = LocalDateTime.now(Clock.systemUTC()).until(targetTime, ChronoUnit.MILLIS);
        if (delay < 0) throw new IllegalArgumentException("Target time is in the past");
        rabbitTemplate.convertAndSend(
                customExchange.getName(),
                taskQueue.getName(),
                repetitionRuleId,
                msg -> {
                    msg.getMessageProperties().setHeader("x-delay", delay);
                    return msg;
                }
        );
        System.out.println(LocalDateTime.now(Clock.systemUTC()) + " Scheduled task for: " + targetTime);
    }
}
