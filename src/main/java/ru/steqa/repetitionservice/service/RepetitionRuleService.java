package ru.steqa.repetitionservice.service;

import org.springframework.stereotype.Service;
import ru.steqa.repetitionservice.repository.IRepetitionRuleRepository;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition;

@Service
public class RepetitionRuleService implements IRepetitionRuleService {
    private final IRepetitionRuleRepository repetitionRuleRepository;

    public RepetitionRuleService(IRepetitionRuleRepository repetitionRuleRepository) {
        this.repetitionRuleRepository = repetitionRuleRepository;
    }

    @Override
    public IntervalSecondRepetition addIntervalSecondsRepetitionRule(IntervalSecondRepetition interval) {
        return repetitionRuleRepository.save(interval);
    }
}
