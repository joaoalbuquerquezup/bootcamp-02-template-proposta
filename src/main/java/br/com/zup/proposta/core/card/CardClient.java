package br.com.zup.proposta.core.card;

import br.com.zup.proposta.core.card.block.BlockCardRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "card-service", url = "${card-service.api-location}")
public interface CardClient {

    @PostMapping(value = "/cartoes")
    void generateCard(CardCreationRequest cardCreationRequest);

    @GetMapping(value = "/cartoes")
    CardRetrieveResponse retrieveCard(@RequestParam UUID idProposta);

    @PostMapping(path = "/cartoes/{creditCardNumber}/bloqueios")
    void blockCard(@PathVariable(name = "creditCardNumber") String creditCardNumber,
                   @RequestBody BlockCardRequest lockRequest);
}
