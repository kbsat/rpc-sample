package com.example.controller;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GrpcClient("helloService")
    private HelloServiceGrpc.HelloServiceBlockingStub helloStub;

     @GetMapping("/test")
     public String rpc() {
         HelloResponse response = helloStub.sayHello(
             HelloRequest.newBuilder()
                 .setName("테스트")
                 .build()
         );

         return response.getMessage();
     }
}
