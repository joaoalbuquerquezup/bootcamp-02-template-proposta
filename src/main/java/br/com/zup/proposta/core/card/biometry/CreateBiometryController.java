package br.com.zup.proposta.core.card.biometry;

import br.com.zup.proposta.core.card.Card;
import br.com.zup.proposta.core.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/card")
public class CreateBiometryController {

    private final CardRepository cardRepository;

    public CreateBiometryController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping("/{cardId}")
    @Transactional
    public ResponseEntity<Void> create (@PathVariable("cardId") UUID cardId,
                                          @Valid @RequestBody CreateBiometryRequest createBiometryRequest,
                                          UriComponentsBuilder uriComponentsBuilder) {

        Optional<Card> optionalCard = this.cardRepository.getById(cardId);
        Card card = optionalCard.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        Biometry biometry = createBiometryRequest.toModel();
        card.addBiometry(biometry);

        URI uri = uriComponentsBuilder.path("/biometry").buildAndExpand(biometry.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
