package ru.steqa.repetitionservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.steqa.repetitionservice.exception.RepetitionRuleNotFound;
import ru.steqa.repetitionservice.grpc.GrpcClientService;
import ru.steqa.repetitionservice.repository.IRepetitionRuleRepository;
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
    private final IRepetitionRuleRepository repetitionRuleRepository;
    private final TaskProducer taskProducer;
    private final GrpcClientService grpcClientService;

    public TaskService(IRepetitionRuleRepository repetitionRuleRepository, TaskProducer taskProducer, GrpcClientService grpcClientService) {
        this.repetitionRuleRepository = repetitionRuleRepository;
        this.taskProducer = taskProducer;
        this.grpcClientService = grpcClientService;
    }

    @Async
    public void executeTask(String repetitionRuleId) {
        BaseRepetitionRule rule = repetitionRuleRepository.findById(repetitionRuleId)
                .orElseThrow(RepetitionRuleNotFound::new);

        if (rule instanceof FixedMonthRepetition) {

        } else if (rule instanceof FixedYearRepetition) {

        } else if (rule instanceof IntervalDayRepetition) {

        } else if (rule instanceof IntervalSecondRepetition) {
            handleIntervalSecondRepetition((IntervalSecondRepetition) rule);
        }
    }

    @Async
    public void handleIntervalSecondRepetition(IntervalSecondRepetition repetitionRule) {
        var now = LocalDateTime.now(Clock.systemUTC());
        while (repetitionRule.nextExecution.isBefore(now) || repetitionRule.nextExecution.isEqual(now)) {
            repetitionRule.nextExecution = repetitionRule.nextExecution.plusSeconds(repetitionRule.seconds);
            grpcClientService.addTransaction(repetitionRule.userId, repetitionRule.transactionId);
        }
        repetitionRuleRepository.save(repetitionRule);

        taskProducer.sendTaskAtTime(repetitionRule.id, repetitionRule.nextExecution);
    }
}