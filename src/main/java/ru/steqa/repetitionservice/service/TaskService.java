package ru.steqa.repetitionservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.steqa.repetitionservice.grpc.GrpcClientService;
import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.FixedMonthRepetition;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.FixedYearRepetition;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalDayRepetition;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition;
import ru.steqa.repetitionservice.utility.TaskProducer;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class TaskService implements ITaskService {
    private final IRepetitionRuleService repetitionRuleService;
    private final TaskProducer taskProducer;
    private final GrpcClientService grpcClientService;

    public TaskService(IRepetitionRuleService repetitionRuleService, TaskProducer taskProducer, GrpcClientService grpcClientService) {
        this.repetitionRuleService = repetitionRuleService;
        this.taskProducer = taskProducer;
        this.grpcClientService = grpcClientService;
    }

    @Async
    public void executeTask(String repetitionRuleId) {
        BaseRepetitionRule rule = repetitionRuleService.getRepetitionRuleById(repetitionRuleId);

        if (rule.deleted) {
            repetitionRuleService.deleteRepetitionRule(repetitionRuleId);
            return;
        }

        if (rule instanceof FixedMonthRepetition) {

        } else if (rule instanceof FixedYearRepetition) {

        } else if (rule instanceof IntervalDayRepetition) {

        } else if (rule instanceof IntervalSecondRepetition) {
            handleIntervalSecondRepetition((IntervalSecondRepetition) rule);
        }
    }

    @Async
    public void handleIntervalSecondRepetition(IntervalSecondRepetition repetitionRule) {
        LocalDateTime nextExecution = repetitionRule.nextExecution;
        var now = LocalDateTime.now(Clock.systemUTC());
        while (nextExecution.isBefore(now) || nextExecution.isEqual(now)) {
            nextExecution = nextExecution.plusSeconds(repetitionRule.seconds);
            grpcClientService.addTransaction(repetitionRule.userId, repetitionRule.transactionId);
        }
        BaseRepetitionRule updatedRepetitionRule = repetitionRuleService.updateRepetitionRuleNextExecution(repetitionRule.id, nextExecution);
        taskProducer.sendTaskAtTime(updatedRepetitionRule.id, updatedRepetitionRule.nextExecution);
    }
}