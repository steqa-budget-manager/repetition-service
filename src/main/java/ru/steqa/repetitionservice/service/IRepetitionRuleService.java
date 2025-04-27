package ru.steqa.repetitionservice.service;

import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition;

public interface IRepetitionRuleService {
    IntervalSecondRepetition addIntervalSecondsRepetitionRule(IntervalSecondRepetition interval);
}
