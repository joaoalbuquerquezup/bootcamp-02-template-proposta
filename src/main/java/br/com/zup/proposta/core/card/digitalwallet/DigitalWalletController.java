package br.com.zup.proposta.core.card.digitalwallet;

import br.com.zup.proposta.core.card.Card;
import br.com.zup.proposta.core.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("/card")
public class DigitalWalletController {

    private final CardRepository cardRepository;
    private final DigitalWalletService digitalWalletService;

    private final TransactionTemplate txTemplate;
    private final EntityManager entityManager;

    public DigitalWalletController(CardRepository cardRepository,
                                   DigitalWalletService digitalWalletService,
                                   TransactionTemplate txTemplate,
                                   EntityManager entityManager) {
        this.cardRepository = cardRepository;
        this.digitalWalletService = digitalWalletService;
        this.txTemplate = txTemplate;
        this.entityManager = entityManager;
    }

    @PostMapping("/{cardId}/digital-wallet")
    public ResponseEntity<Void> create(@PathVariable UUID cardId,
                                       @Valid @RequestBody AddDigitalWalletRequest addDigitalWalletRequest,
                                       HttpServletRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Optional<Card> optionalCard = this.cardRepository.findByIdWithDigitalWallet(cardId);
        Card card = optionalCard.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        JwtAuthenticationToken principal = (JwtAuthenticationToken) request.getUserPrincipal();
        Object emailFromRequest = principal.getTokenAttributes().get("email");
        if (!(card.getProposal().getEmail().equals(emailFromRequest))) throw new ResponseStatusException(FORBIDDEN);

        String walletIdAtExternalSystem = this.digitalWalletService.informDigitalWallet(card.getNumber(), addDigitalWalletRequest);
        DigitalWallet digitalwallet = addDigitalWalletRequest.toModel(walletIdAtExternalSystem);

        txTemplate.execute(txStatus -> {
            this.entityManager.persist(digitalwallet);

            boolean walletWasIncluded = card.addWallet(digitalwallet);
            if (!walletWasIncluded) throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
            this.cardRepository.save(card);

            return txStatus;
        });

        URI uri = uriComponentsBuilder.path("/digital-wallet/{id}").buildAndExpand(digitalwallet.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
