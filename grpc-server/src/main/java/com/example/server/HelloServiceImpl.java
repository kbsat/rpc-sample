package com.example.server;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String message = "Hello, " + request.getName();
        HelloResponse response = HelloResponse.newBuilder()
            .setMessage(message)
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
