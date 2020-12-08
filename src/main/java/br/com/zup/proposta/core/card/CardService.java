package br.com.zup.proposta.core.card;

import br.com.zup.proposta.core.proposal.Proposal;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardService.class);

    private final CardClient cardClient;

    public CardService(CardClient cardClient) {
        this.cardClient = cardClient;
    }

    public Optional<Card> retrieveCardByProposal(Proposal proposal) {

        try {
            CardRetrieveResponse cardRetrieveResponse = this.cardClient.retrieveCard(proposal.getId());
            Card card = new Card(cardRetrieveResponse.getCardNumber(), proposal);

            return Optional.of(card);

        } catch (FeignException e) {
            LOGGER.error("Error at card generation | Status code: {}, Body: {}, Message: {}",
                    e.status(),
                    e.contentUTF8(),
                    e.getMessage());

            return Optional.empty();
        }
    }
}
