package org.fernando.meu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.eclipse.microprofile.reactive.messaging.*;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Slf4j
@ApplicationScoped
public class FernandoKafka {

    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
    public static final String ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";

    @Inject
    @Channel("kafka-test") // Emit on the channel 'orders'
    Emitter<String> kafkaTestes;

    @Inject
    @Channel(ECOMMERCE_NEW_ORDER)
    Emitter<OrderDomain> ecommerceNewOrderProducer;

    @Inject
    @Channel(ECOMMERCE_NEW_ORDER)
    Emitter<Message<OrderDomain>> noE;

    @Inject
    @Channel(ECOMMERCE_NEW_ORDER)
    Emitter<KafkaRecord<String, OrderDomain>> krOP;

    @Inject
    @Channel(ECOMMERCE_SEND_EMAIL)
    Emitter<String> ecommerceSendEmailProducer;


//    private BlockingQueue<OrderDomain> messages = new LinkedBlockingQueue<>();
//
//    public void addOrderQueue(OrderDomain message) {
//        messages.add(message);
//
//    }


    public void produce(String message) throws JsonProcessingException {
        log.info("Postando " + message);
        final RecomentacaoEventDomain payload = new RecomentacaoEventDomain();
        payload.setData(message);
        payload.setSinistroId(55L);

        var o = new ObjectMapper();

        kafkaTestes.send(o.writeValueAsString(payload)).thenAccept(t -> System.out.println("sucesso"));
    }


//    @Outgoing(ECOMMERCE_NEW_ORDER)
//    public CompletionStage<KafkaRecord<String, OrderDomain>> sendOrder() {
//        return CompletableFuture.supplyAsync(() -> {
//            OrderDomain vote = null;
//            try {
//                vote = messages.take();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Enviando ordem via Outgoing");
//                return KafkaRecord.of(UUID.randomUUID().toString(), vote);
//        });
//    }


    @Incoming("kafka-test")
    @Outgoing("kafka-test")
    public String logKafaTest(String message) {
        try {

            int e = 8/0;
        }catch (Exception e) {
            System.out.println("erro");
        }
        System.out.println("log msg Incomming " + message);
        return message;
    }

    @Incoming("fefeevent")
    public void fefeListener(String message){
        System.out.println("processado topic fefeevent " + message);
    }

}
