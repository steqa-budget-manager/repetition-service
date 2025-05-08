package ru.steqa.repetitionservice.scheme.rabbit.repetition;

import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.RepetitionMode;
import ru.steqa.repetitionservice.scheme.rabbit.TransactionType;

import java.time.LocalDateTime;

public class IntervalDayRepetition extends BaseRepetitionRule {
    public Integer days;

    public IntervalDayRepetition(Long userId, Long transactionId, TransactionType transactionType,
                                 LocalDateTime nextExecution, Integer days) {
        super(userId, transactionId, transactionType, nextExecution, RepetitionMode.INTERVAL_DAY);
        this.days = days;
    }
}
