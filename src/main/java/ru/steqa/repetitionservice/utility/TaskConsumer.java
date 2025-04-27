package ru.steqa.repetitionservice.utility;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.steqa.repetitionservice.service.ITaskService;

@Service
public class TaskConsumer {
    private final ITaskService taskService;

    public TaskConsumer(ITaskService taskService) {
        this.taskService = taskService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.task-queue}")
    public void receiveTask(String taskName) {
        taskService.executeTask(taskName);
    }
}