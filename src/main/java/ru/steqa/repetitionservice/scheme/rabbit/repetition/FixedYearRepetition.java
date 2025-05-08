package ru.steqa.repetitionservice.scheme.rabbit.repetition;

import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.RepetitionMode;
import ru.steqa.repetitionservice.scheme.rabbit.TransactionType;

import java.time.LocalDateTime;

public class FixedYearRepetition extends BaseRepetitionRule {
    public Integer day;
    public Integer month;

    public FixedYearRepetition(Long userId, Long transactionId, TransactionType transactionType,
                               LocalDateTime nextExecution, Integer day, Integer month) {
        super(userId, transactionId, transactionType, nextExecution, RepetitionMode.FIXED_YEAR);
        this.day = day;
        this.month = month;
    }
}
