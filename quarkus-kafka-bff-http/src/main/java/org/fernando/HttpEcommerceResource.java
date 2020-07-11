package org.fernando;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import lombok.var;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.fernando.meu.FernandoKafka;
import org.fernando.meu.OrderDomain;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Path("/ecommerce")
public class HttpEcommerceResource {

    @Inject
    FernandoKafka fernandoKafka;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String produce(@QueryParam("email") String email,
                          @QueryParam("amount") BigDecimal amount) throws Exception {

        System.out.println("Produzindo nova order via quarkus");

        String orderId =  UUID.randomUUID().toString();
        OrderDomain o = new OrderDomain(orderId,amount, email );

//        fernandoKafka.addOrderQueue(o);
//        fernandoKafka.getKrOP().send(KafkaRecord.of(UUID.randomUUID().toString(), o))
//        .thenAccept( e -> System.out.println("sucesso postar orderm kafka record"));

//        Message<OrderDomain> ms = Message.of(o);
//        fernandoKafka.getNoE().send(ms);

        fernandoKafka.getEcommerceNewOrderProducer()
                .send(o).toCompletableFuture().get();

        fernandoKafka.getEcommerceSendEmailProducer()
                .send("Obrigado por acessar: " + email);


        System.out.println("postando msg: "+ o);
        return "ok";
    }
}
