package br.com.zup.proposta.core.card.lock;

import br.com.zup.proposta.core.card.CardClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class BlockCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockCardService.class);

    @Value("${spring.application.name}")
    private String appName;

    private final CardClient cardClient;

    public BlockCardService(CardClient cardClient) {
        this.cardClient = cardClient;
    }

    public void blockCard(String cardNumber) {

        try {
            BlockCardRequest blockCardRequest = new BlockCardRequest(this.appName);
            this.cardClient.blockCard(cardNumber, blockCardRequest);

        } catch (FeignException e) {
            LOGGER.error("Error at card blocking | Status code: {}, Body: {}, Message: {}",
                    e.status(),
                    e.contentUTF8(),
                    e.getMessage());

            throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
        }
    }
}
