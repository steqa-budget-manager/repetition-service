package ru.steqa.repetitionservice.service;

import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalDayRepetition;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition;

import java.time.LocalDateTime;

public interface IRepetitionRuleService {
    IntervalSecondRepetition addIntervalSecondRepetitionRule(IntervalSecondRepetition interval);
    IntervalDayRepetition addIntervalDayRepetitionRule(IntervalDayRepetition interval);
    BaseRepetitionRule getRepetitionRuleById(String id);
    BaseRepetitionRule updateRepetitionRuleNextExecution(String id, LocalDateTime nextExecution);
    void updateRepetitionRuleDeleted(String id, Boolean deleted);
    void deleteRepetitionRule(String id);
}
