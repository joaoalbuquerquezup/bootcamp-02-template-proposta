package br.com.zup.proposta.core.card.travelnotice;

import br.com.zup.proposta.core.card.Card;
import br.com.zup.proposta.core.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/card")
public class TravelNoticeController {

    private final CardRepository cardRepository;
    private final TravelNoticeService travelNoticeService;

    private final TransactionTemplate txTemplate;

    public TravelNoticeController(CardRepository cardRepository,
                                  TravelNoticeService travelNoticeService,
                                  TransactionTemplate txTemplate) {
        this.cardRepository = cardRepository;
        this.travelNoticeService = travelNoticeService;
        this.txTemplate = txTemplate;
    }

    @PostMapping("/{cardId}/travel-notice")
    public ResponseEntity<Void> create(@PathVariable("cardId") UUID cardId,
                                       @RequestHeader(value = "User-Agent") String userAgent,
                                       HttpServletRequest request,
                                       @Valid @RequestBody CardTravelNoticeRequest cardTravelNoticeRequest) {

        Optional<Card> optionalCard = this.cardRepository.findById(cardId);
        Card card = optionalCard.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        JwtAuthenticationToken principal = (JwtAuthenticationToken) request.getUserPrincipal();
        Object emailFromRequest = principal.getTokenAttributes().get("email");
        if (!(card.getProposal().getEmail().equals(emailFromRequest))) throw new ResponseStatusException(FORBIDDEN);

        this.travelNoticeService.noticeTravel(card.getNumber(), cardTravelNoticeRequest);

        txTemplate.execute(txStatus -> {
            CardTravelNotice cardTravelNotice = cardTravelNoticeRequest.toModel(userAgent, request.getRemoteAddr());
            card.informTravelNotice(cardTravelNotice);

            return txStatus;
        });

        return ResponseEntity.ok().build();
    }
}
