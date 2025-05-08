package ru.steqa.repetitionservice.service;

import org.springframework.stereotype.Service;
import ru.steqa.repetitionservice.exception.RepetitionRuleNotFound;
import ru.steqa.repetitionservice.repository.IRepetitionRuleRepository;
import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.FixedMonthRepetition;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalDayRepetition;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition;

import java.time.LocalDateTime;

@Service
public class RepetitionRuleService implements IRepetitionRuleService {
    private final IRepetitionRuleRepository repetitionRuleRepository;

    public RepetitionRuleService(IRepetitionRuleRepository repetitionRuleRepository) {
        this.repetitionRuleRepository = repetitionRuleRepository;
    }

    @Override
    public IntervalSecondRepetition addIntervalSecondRepetitionRule(IntervalSecondRepetition repetition) {
        return repetitionRuleRepository.save(repetition);
    }

    @Override
    public IntervalDayRepetition addIntervalDayRepetitionRule(IntervalDayRepetition repetition) {
        return repetitionRuleRepository.save(repetition);
    }

    @Override
    public FixedMonthRepetition addFixedMonthRepetitionRule(FixedMonthRepetition repetition) {
        return repetitionRuleRepository.save(repetition);
    }

    @Override public BaseRepetitionRule getRepetitionRuleById(String id) {
        return repetitionRuleRepository.findById(id)
                .orElseThrow(RepetitionRuleNotFound::new);
    }

    @Override
    public BaseRepetitionRule updateRepetitionRuleNextExecution(String id, LocalDateTime nextExecution) {
        BaseRepetitionRule rule = repetitionRuleRepository.findById(id)
                .orElseThrow(RepetitionRuleNotFound::new);
        rule.nextExecution = nextExecution;
        return repetitionRuleRepository.save(rule);
    }

    @Override public void updateRepetitionRuleDeleted(String id, Boolean deleted) {
        BaseRepetitionRule rule = repetitionRuleRepository.findById(id)
                .orElseThrow(RepetitionRuleNotFound::new);
        rule.deleted = deleted;
        repetitionRuleRepository.save(rule);
    }

    @Override
    public void deleteRepetitionRule(String id) {
        repetitionRuleRepository.findById(id)
                .ifPresentOrElse(repetitionRuleRepository::delete, () -> {
                    throw new RepetitionRuleNotFound();
                });
    }
}
