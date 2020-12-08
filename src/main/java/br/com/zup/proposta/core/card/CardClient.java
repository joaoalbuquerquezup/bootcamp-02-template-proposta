package br.com.zup.proposta.core.card;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "card-service", url= "${card-service.api-location}")
public interface CardClient {

    @PostMapping(value = "/cartoes")
    void generateCard(CardCreationRequest cardCreationRequest);

    @GetMapping(value = "/cartoes")
    CardRetrieveResponse retrieveCard(@RequestParam UUID idProposta);
}
