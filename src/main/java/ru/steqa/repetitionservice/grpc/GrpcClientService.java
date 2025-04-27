package ru.steqa.repetitionservice.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.steqa.grpc.AddTransactionRequest;
import ru.steqa.grpc.AddTransactionResponse;
import ru.steqa.grpc.ApiServiceGrpc;

@Service
public class GrpcClientService {
    @GrpcClient("service")
    private ApiServiceGrpc.ApiServiceBlockingStub blockingStub;

    public void addTransaction(Long userId, Long transactionId) {
        AddTransactionRequest request = AddTransactionRequest.newBuilder()
                .setUserId(userId)
                .setTransactionId(transactionId)
                .build();

        AddTransactionResponse response = blockingStub.addTransaction(request);
    }
}
