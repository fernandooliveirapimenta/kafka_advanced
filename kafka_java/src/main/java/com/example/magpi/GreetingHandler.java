package com.example.magpi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GreetingHandler {

  public Mono<ServerResponse> hello(ServerRequest request) {
    log.info("Request {}", request);
    System.out.println(request.attribute("fernando"));
    String valor = " ola ";
     if(request.exchange().getRequest().getQueryParams().containsKey("fernando"))
     valor = request.exchange().getRequest().getQueryParams().get("fernando")
            .stream()
            .findFirst()
            .orElse(null);

    String retorno = String.format("Hello, Spring Alterado! %s", valor);
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
      .body(BodyInserters.fromValue(retorno));
  }
}