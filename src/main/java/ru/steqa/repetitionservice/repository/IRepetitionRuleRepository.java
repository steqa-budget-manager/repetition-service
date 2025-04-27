package ru.steqa.repetitionservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.steqa.repetitionservice.scheme.rabbit.BaseRepetitionRule;

import java.util.Optional;

public interface IRepetitionRuleRepository extends MongoRepository<BaseRepetitionRule, Long> {
    Optional<BaseRepetitionRule> findById(String id);
}
