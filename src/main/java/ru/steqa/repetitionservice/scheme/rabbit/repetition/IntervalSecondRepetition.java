package ru.steqa.repetitionservice.scheme.rabbit.repetition;

import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.RepetitionMode;
import ru.steqa.repetitionservice.scheme.rabbit.TransactionType;

import java.time.LocalDateTime;

public class IntervalSecondRepetition extends BaseRepetitionRule {
    public Long seconds;

    public IntervalSecondRepetition(Long userId, Long transactionId, TransactionType transactionType,
                                    LocalDateTime nextExecution, Long seconds) {
        super(userId, transactionId, transactionType, nextExecution, RepetitionMode.INTERVAL_SECOND);
        this.seconds = seconds;
    }
}
