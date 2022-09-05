package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ReceiverVerticle2 extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    System.out.println("ReceiverVerticle2 실행");
    System.out.println("ReceiverVerticle2 : "+vertx.getOrCreateContext().deploymentID());
    System.out.println("ReceiverVerticle2 : "+vertx.getOrCreateContext().getInstanceCount());
    vertx.eventBus().consumer("send",msg->{
      System.out.println("리시버 2 send 1 수신 : " + msg.address() + " : "+ msg.headers() + " : "+msg.body());
;
    });
    vertx.eventBus().consumer("send",msg->{
      System.out.println("리시버 2 send 2 수신 : " + msg.address() + " : "+ msg.headers() + " : "+msg.body());
    });

    vertx.eventBus().consumer("publish",msg->{
      System.out.println("리시버 2 publish 1 수신 : " + msg.address() + " : "+ msg.headers() + " : "+msg.body());
    });

    vertx.eventBus().consumer("publish",msg->{
      System.out.println("리시버 2 publish 2 수신 : " + msg.address() + " : "+ msg.headers() + " : "+msg.body());
    });

    vertx.eventBus().consumer("request-response",msg->{
      System.out.println("리시버 2 request-response 수신 : " + msg.address() + " : "+ msg.headers() + " : "+msg.body());
      msg.reply("request-response 리시버 2 응답 리플");
    });

    startPromise.complete();
    System.out.println("ReceiverVerticle2 사이즈! " + vertx.deploymentIDs().size());
    vertx.deploymentIDs().stream().forEach(ids->System.out.println("ReceiverVerticle2 아이디즈"+ ids));
    System.out.println("ReceiverVerticle2 ready");
  }

}
