package br.com.zup.proposta.core.card.digitalwallet;

import br.com.zup.proposta.core.card.CardClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class DigitalWalletService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalWalletService.class);

    private final CardClient cardClient;

    public DigitalWalletService(CardClient cardClient) {
        this.cardClient = cardClient;
    }

    public String informDigitalWallet(String cardNumber, AddDigitalWalletRequest request) {

        try {
            var informDigitalWalletRequest = new InformDigitalWalletRequest(request);
            var response = this.cardClient.informDigitalWallet(cardNumber, informDigitalWalletRequest);
            return response.getWalletIdAtExternalSystem();

        } catch (FeignException e) {
            LOGGER.error("Error creating digital wallet for card {} | Status code: {}, Body: {}, Message: {}",
                    cardNumber,
                    e.status(),
                    e.contentUTF8(),
                    e.getMessage());

            throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
        }
    }


}
