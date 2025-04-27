package ru.steqa.repetitionservice.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.steqa.grpc.*;
import ru.steqa.repetitionservice.scheme.rabbit.RepetitionMode;
import ru.steqa.repetitionservice.scheme.rabbit.TransactionType;
import ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition;
import ru.steqa.repetitionservice.service.IRepetitionRuleService;
import ru.steqa.repetitionservice.utility.TaskProducer;
import ru.steqa.repetitionservice.utility.TimeUtility;

import java.time.LocalDateTime;

@GrpcService
public class GrpcServerService extends RepetitionServiceGrpc.RepetitionServiceImplBase {
    private final TimeUtility timeUtility;
    private final IRepetitionRuleService repetitionRuleService;
    private final TaskProducer taskProducer;

    public GrpcServerService(TimeUtility timeUtility, IRepetitionRuleService repetitionRuleService, TaskProducer taskProducer) {
        this.timeUtility = timeUtility;
        this.repetitionRuleService = repetitionRuleService;
        this.taskProducer = taskProducer;
    }

    @Override
    public void addRule(AddRuleRequest request, StreamObserver<RuleIdResponse> responseObserver) {
        String mode = request.getMode().name();
        Long userId = request.getUserId();
        Long transactionId = request.getTransactionId();
        TransactionType transactionType = TransactionType.valueOf(request.getTransactionType().name());
        LocalDateTime nextExecution = timeUtility.longToLocalDateTime(request.getNextExecution());
        String addedRuleId = null;

        if (mode.equals(RepetitionMode.INTERVAL_SECOND.name())) {
            IntervalSecondRepetition intervalSecondRepetition = new IntervalSecondRepetition(
                    userId,
                    transactionId,
                    transactionType,
                    nextExecution,
                    request.getInterval().getSeconds()
            );
            IntervalSecondRepetition repetition = repetitionRuleService.addIntervalSecondsRepetitionRule(intervalSecondRepetition);
            addedRuleId = repetition.id;

            taskProducer.sendTaskAtTime(repetition.id, nextExecution);
        }

        RuleIdResponse response = RuleIdResponse.newBuilder()
                .setRuleId(addedRuleId)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void deleteRule(DeleteRuleRequest request, StreamObserver<StatusResponse> responseObserver) {
        repetitionRuleService.updateRepetitionRuleDeleted(request.getRuleId(), true);

        StatusResponse response = StatusResponse.newBuilder()
                .setStatus(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
