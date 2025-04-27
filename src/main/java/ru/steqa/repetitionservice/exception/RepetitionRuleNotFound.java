package ru.steqa.repetitionservice.exception;

public class RepetitionRuleNotFound extends RuntimeException {
    public RepetitionRuleNotFound() {
        super("Repetition rule not found");
    }
}
