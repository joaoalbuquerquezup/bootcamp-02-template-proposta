package br.com.zup.proposta.core.card.travelnotice;

import br.com.zup.proposta.core.card.CardClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class TravelNoticeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelNoticeService.class);

    private final CardClient cardClient;

    public TravelNoticeService(CardClient cardClient) {
        this.cardClient = cardClient;
    }

    public void noticeTravel(String cardNumber, CardTravelNoticeRequest cardTravelNoticeRequest) {

        try {
            CardTravelNoticeRequestForLegacy request = new CardTravelNoticeRequestForLegacy(cardTravelNoticeRequest);
            this.cardClient.noticeTravel(cardNumber, request);

        } catch (FeignException e) {
            LOGGER.error("Error at noticing travel for card {} | Status code: {}, Body: {}, Message: {}",
                    cardNumber,
                    e.status(),
                    e.contentUTF8(),
                    e.getMessage());

            throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
        }
    }
}
