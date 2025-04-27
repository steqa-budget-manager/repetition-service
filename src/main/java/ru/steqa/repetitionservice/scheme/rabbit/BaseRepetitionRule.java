package ru.steqa.repetitionservice.scheme.rabbit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("repetitionRule")
public class BaseRepetitionRule {
    @Id
    public String id;
    public Long userId;
    public Long transactionId;
    public TransactionType transactionType;
    public LocalDateTime nextExecution;
    public RepetitionMode mode;

    public BaseRepetitionRule(Long userId, Long transactionId, TransactionType transactionType,
                              LocalDateTime nextExecution, RepetitionMode mode) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.nextExecution = nextExecution;
        this.mode = mode;
    }
}
