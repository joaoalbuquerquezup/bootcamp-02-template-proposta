package br.com.zup.proposta.core.card.lock;

import br.com.zup.proposta.core.card.Card;
import br.com.zup.proposta.core.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/card")
public class LockCardController {

    private final CardRepository cardRepository;

    public LockCardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping("/{cardId}/block")
    @Transactional
    public ResponseEntity<Void> create(@PathVariable("cardId") UUID cardId,
                                       @RequestHeader(value = "User-Agent") String userAgent,
                                       HttpServletRequest request) {

        Optional<Card> optionalCard = this.cardRepository.findById(cardId);
        Card card = optionalCard.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        JwtAuthenticationToken principal = (JwtAuthenticationToken) request.getUserPrincipal();
        Object emailFromRequest = principal.getTokenAttributes().get("email");
        if (!(card.getProposal().getEmail().equals(emailFromRequest))) throw new ResponseStatusException(FORBIDDEN);

        BlockCard blockCard = new BlockCard(userAgent, request.getRemoteAddr());
        card.block(blockCard);

        return ResponseEntity.ok().build();
    }
}
