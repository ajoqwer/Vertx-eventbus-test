package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class SenderVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("SenderVerticle 실행");
    System.out.println("SenderVerticle : "+vertx.getOrCreateContext().deploymentID());
    System.out.println("SenderVerticle : "+vertx.getOrCreateContext().getInstanceCount());

    vertx.setTimer(1000,r ->{

      JsonObject jo = new JsonObject();
      jo.put("msg","메세지 publish/subscribe 발송이다 이자식아 ");
      jo.put("type","send");
      vertx.eventBus().send("send",jo);
      jo.put("type","publish");
      vertx.eventBus().publish("publish",jo);

      jo.put("msg","메세지 point to point 발송이다 이자식아 ");
      jo.put("type","request-response");
      vertx.eventBus().request("request-response",jo, reply->{
        if(reply.succeeded()){
          System.out.println("SenderVerticle" + reply.result().headers() +" : "+ reply.result().address() +" : "+ reply.result().body());
        }else {
          reply.cause().printStackTrace();
        }
      });

    });
    startPromise.complete();
    System.out.println("SenderVerticle 사이즈! " + vertx.deploymentIDs().size());
    vertx.deploymentIDs().stream().forEach(ids->System.out.println("SenderVerticle 아이디즈"+ ids));
    System.out.println("SenderVerticle 준비됨");
  }
}
