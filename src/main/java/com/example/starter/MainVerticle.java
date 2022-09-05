package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 *
 *   EventBus eventbus = vertx.eventbus(); eventbus 클래스의 메소드
 *   publish() => 현재 인스턴스 내의 해당 address 를 참조하고 있는 모든 consumer 에서 데이터 수신.
 *   send(), request() => 해당 address 를 참조하고 있어도 먼저 닿은 consumer 하나만 데이터 수신.
 *   reqeust() => reply 로 데이터 수신성공시  응답 핸들러 등록 가능.
 *   Clustering EventBus 구현을 통해   다른 Vertx인스턴스에서 eventbus 통신 가능.
 *
 */
public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ReceiverVerticle());
    vertx.deployVerticle(new ReceiverVerticle2());
    vertx.deployVerticle(new SenderVerticle());


    System.out.println("메인 디플로이 사이즈! " + vertx.deploymentIDs().size());
    vertx.deploymentIDs().stream().forEach(ids->System.out.println("메인 디플로이 아이디즈"+ ids));
    vertx.setTimer(1500, v->{
      System.out.println("메인 디플로이 사이즈! " + vertx.deploymentIDs().size());
      vertx.deploymentIDs().stream().forEach(ids->System.out.println("메인 디플로이 아이디즈"+ ids));
      System.out.println("메인 : "+vertx.getOrCreateContext().deploymentID());
      System.out.println("메인 : "+vertx.getOrCreateContext().getInstanceCount());
    });

    System.out.println( "메인메소드  비동기실행 체크");

  };


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("이건 실행 안되지롱 ~  메인버티클");
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
