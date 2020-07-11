package org.fernando;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.fernando.meu.FernandoKafka;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/recomendation")
public class HttpRecomendacaoProdutoResource {

    @Inject
    private FernandoKafka fernandoKafka;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String produce(@QueryParam("msg") String msg) throws JsonProcessingException {
        System.out.println("postando msg: "+ msg);
        fernandoKafka.produce(msg);
        return "ok";
    }

}