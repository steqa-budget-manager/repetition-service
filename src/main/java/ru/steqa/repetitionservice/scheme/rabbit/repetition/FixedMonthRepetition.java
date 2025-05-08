package ru.steqa.repetitionservice.scheme.rabbit.repetition;

import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.RepetitionMode;
import ru.steqa.repetitionservice.scheme.rabbit.TransactionType;

import java.time.LocalDateTime;

public class FixedMonthRepetition extends BaseRepetitionRule {
    public Integer day;

    public FixedMonthRepetition(Long userId, Long transactionId, TransactionType transactionType,
                                LocalDateTime nextExecution, Integer day) {
        super(userId, transactionId, transactionType, nextExecution, RepetitionMode.FIXED_MONTH);
        this.day = day;
    }
}
