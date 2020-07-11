package org.fernando.meu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.eclipse.microprofile.reactive.messaging.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;

@Slf4j
@ApplicationScoped
public class FernandoKafka {

    @Inject
    @Channel("kafka-test") // Emit on the channel 'orders'
    Emitter<String> kafkaTestes;


    public void produce(String message) throws JsonProcessingException {
        log.info("Postando " + message);
        final RecomentacaoEventDomain payload = new RecomentacaoEventDomain();
        payload.setData(message);
        payload.setSinistroId(55L);

        var o = new ObjectMapper();

        kafkaTestes.send(o.writeValueAsString(payload)).thenAccept(t -> System.out.println("sucesso"));
    }


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
