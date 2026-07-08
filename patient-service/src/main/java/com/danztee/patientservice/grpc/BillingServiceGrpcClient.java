package com.danztee.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ImportGrpcClients(target = "billing", types = BillingServiceGrpc.BillingServiceBlockingStub.class)
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    //    localhost:9001/BillingService/CreatePatientAccount
//    aws.grpc:123456/BillingService/CreatePatientAccount
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.port:9001}") int serverPort
    ) {
        log.info("Connecting to billing service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext().build();

        billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String firstName,
                                                String lastName, String email, String phoneNumber) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();


        BillingResponse response = billingServiceBlockingStub.createBillingAccount(request);

        log.info("Billing account created for patient with ID: {}", patientId);

        return response;

    }
}
