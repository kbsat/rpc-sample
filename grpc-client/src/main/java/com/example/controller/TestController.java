package com.example.controller;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GrpcClient("helloService")
    private HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    @GrpcClient("helloService")
    private HelloServiceGrpc.HelloServiceStub nonblockingStub;

    @GetMapping("/test")
    public String rpc() {
        HelloResponse response = blockingStub.sayHello(
            HelloRequest.newBuilder()
                .setName("테스트")
                .build()
        );

        return response.getMessage();
    }

    @GetMapping("/test/nonblocking")
    public String rpcNonBlocking() {
        HelloRequest request = HelloRequest.newBuilder().setName("비동기 테스트").build();
        nonblockingStub.sayHello(request, new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                System.out.println("[onNext] 응답 도착: " + helloResponse.getMessage());
                System.out.println("[onNext] 현재 쓰레드: " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("[onError] 쓰레드: " + Thread.currentThread().getName());
            }

            @Override
            public void onCompleted() {
                System.out.println("[onCompleted] 완료 쓰레드: " + Thread.currentThread().getName());
            }
        });

        System.out.println("[Main Thread] 이 라인은 바로 실행됩니다! 쓰레드: " + Thread.currentThread().getName());
        return "비동기 요청 완료";
    }
}
